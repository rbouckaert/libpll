/**
 *
 */
package pll;

import java.util.*;
import java.util.logging.Logger;

/**
 * @author Marc Suchard
 * @author Andrew Rambaut
 *
 */
public class PLLFactory {

    private static Map<Integer, ResourceDetails> resourceDetailsMap = new HashMap<Integer, ResourceDetails>();

    public static String getVersionInformation() {
        getPLLJNIWrapper();

        if (PLLJNIWrapper.INSTANCE != null) {

            try {
                return PLLJNIWrapper.INSTANCE.getCitation();
            } catch (UnsatisfiedLinkError ule) {
            	
            }
        }

        return "PLL not installed/found";
    }

    public static String getVersion() {
        getPLLJNIWrapper();

        if (PLLJNIWrapper.INSTANCE != null) {

            try {
                return PLLJNIWrapper.INSTANCE.getVersion();
            } catch (UnsatisfiedLinkError ule) {
                return "?.?";
            }
        }

        return "PLL not installed/found";
    }

    public static List<ResourceDetails> getResourceDetails() {
        getPLLJNIWrapper();

        return new ArrayList<ResourceDetails>(resourceDetailsMap.values());
    }

    public static ResourceDetails getResourceDetails(int resourceNumber) {
        getPLLJNIWrapper();
//        System.err.println("resourceNumber = "+resourceNumber);
        return resourceDetailsMap.get(resourceNumber);
    }

    public static PLL loadPLLInstance(
            int tipCount,
            int partialsBufferCount,
            int compactBufferCount,
            int stateCount,
            int patternCount,
            int eigenBufferCount,
            int matrixBufferCount,
            int categoryCount,
            int scaleBufferCount,
            int[] resourceList,
            long preferenceFlags,
            long requirementFlags
    ) {

        boolean forceJava = Boolean.valueOf(System.getProperty("java.only"));
//        boolean forceHybrid = Boolean.valueOf(System.getProperty("force.hybrid"));

        getPLLJNIWrapper();

        if (!forceJava && PLLJNIWrapper.INSTANCE != null) {

            try {
                PLL PLL = new PLLJNIImpl(
                        tipCount,
                        partialsBufferCount,
                        compactBufferCount,
                        stateCount,
                        patternCount,
                        eigenBufferCount,
                        matrixBufferCount,
                        categoryCount,
                        scaleBufferCount,
                        resourceList,
                        preferenceFlags,
                        requirementFlags
                );

                // In order to know that it was a CPU instance created, we have to let PLL
                // to make the instance and then override it...

                InstanceDetails details = PLL.getDetails();

                if (details != null) // If resourceList/requirements not met, details == null here
                    return PLL;

            } catch (PLLException PLLException) {
                Logger.getLogger("PLL").info("  "+PLLException.getMessage());
            }
        }

        throw new RuntimeException("No acceptable PLL library plugins found. " +
                "Make sure that PLL is properly installed or try changing resource requirements.");
    }

    private static PLLJNIWrapper getPLLJNIWrapper() {
        if (PLLJNIWrapper.INSTANCE == null) {
            try {
                PLLJNIWrapper.loadPLLLibrary();
//                System.err.println("PLL library loaded");

            } catch (UnsatisfiedLinkError ule) {
                System.err.println("Failed to load PLL library: " + ule.getMessage());
            }

            if (PLLJNIWrapper.INSTANCE != null) {
                for (ResourceDetails details : PLLJNIWrapper.INSTANCE.getResourceList()) {
                    resourceDetailsMap.put(details.getNumber(), details);
                }
            }

        }

        return PLLJNIWrapper.INSTANCE;
    }


    // Code and constants for test main()

    private final static String human = "AGAAATATGTCTGATAAAAGAGTTACTTTGATAGAGTAAATAATAGGAGCTTAAACCCCCTTATTTCTACTAGGACTATGAGAATCGAACCCATCCCTGAGAATCCAAAATTCTCCGTGCCACCTATCACACCCCATCCTAAGTAAGGTCAGCTAAATAAGCTATCGGGCCCATACCCCGAAAATGTTGGTTATACCCTTCCCGTACTAAGAAATTTAGGTTAAATACAGACCAAGAGCCTTCAAAGCCCTCAGTAAGTTG-CAATACTTAATTTCTGTAAGGACTGCAAAACCCCACTCTGCATCAACTGAACGCAAATCAGCCACTTTAATTAAGCTAAGCCCTTCTAGACCAATGGGACTTAAACCCACAAACACTTAGTTAACAGCTAAGCACCCTAATCAAC-TGGCTTCAATCTAAAGCCCCGGCAGG-TTTGAAGCTGCTTCTTCGAATTTGCAATTCAATATGAAAA-TCACCTCGGAGCTTGGTAAAAAGAGGCCTAACCCCTGTCTTTAGATTTACAGTCCAATGCTTCA-CTCAGCCATTTTACCACAAAAAAGGAAGGAATCGAACCCCCCAAAGCTGGTTTCAAGCCAACCCCATGGCCTCCATGACTTTTTCAAAAGGTATTAGAAAAACCATTTCATAACTTTGTCAAAGTTAAATTATAGGCT-AAATCCTATATATCTTA-CACTGTAAAGCTAACTTAGCATTAACCTTTTAAGTTAAAGATTAAGAGAACCAACACCTCTTTACAGTGA";
    private final static String chimp = "AGAAATATGTCTGATAAAAGAATTACTTTGATAGAGTAAATAATAGGAGTTCAAATCCCCTTATTTCTACTAGGACTATAAGAATCGAACTCATCCCTGAGAATCCAAAATTCTCCGTGCCACCTATCACACCCCATCCTAAGTAAGGTCAGCTAAATAAGCTATCGGGCCCATACCCCGAAAATGTTGGTTACACCCTTCCCGTACTAAGAAATTTAGGTTAAGCACAGACCAAGAGCCTTCAAAGCCCTCAGCAAGTTA-CAATACTTAATTTCTGTAAGGACTGCAAAACCCCACTCTGCATCAACTGAACGCAAATCAGCCACTTTAATTAAGCTAAGCCCTTCTAGATTAATGGGACTTAAACCCACAAACATTTAGTTAACAGCTAAACACCCTAATCAAC-TGGCTTCAATCTAAAGCCCCGGCAGG-TTTGAAGCTGCTTCTTCGAATTTGCAATTCAATATGAAAA-TCACCTCAGAGCTTGGTAAAAAGAGGCTTAACCCCTGTCTTTAGATTTACAGTCCAATGCTTCA-CTCAGCCATTTTACCACAAAAAAGGAAGGAATCGAACCCCCTAAAGCTGGTTTCAAGCCAACCCCATGACCTCCATGACTTTTTCAAAAGATATTAGAAAAACTATTTCATAACTTTGTCAAAGTTAAATTACAGGTT-AACCCCCGTATATCTTA-CACTGTAAAGCTAACCTAGCATTAACCTTTTAAGTTAAAGATTAAGAGGACCGACACCTCTTTACAGTGA";
    private final static String gorilla = "AGAAATATGTCTGATAAAAGAGTTACTTTGATAGAGTAAATAATAGAGGTTTAAACCCCCTTATTTCTACTAGGACTATGAGAATTGAACCCATCCCTGAGAATCCAAAATTCTCCGTGCCACCTGTCACACCCCATCCTAAGTAAGGTCAGCTAAATAAGCTATCGGGCCCATACCCCGAAAATGTTGGTCACATCCTTCCCGTACTAAGAAATTTAGGTTAAACATAGACCAAGAGCCTTCAAAGCCCTTAGTAAGTTA-CAACACTTAATTTCTGTAAGGACTGCAAAACCCTACTCTGCATCAACTGAACGCAAATCAGCCACTTTAATTAAGCTAAGCCCTTCTAGATCAATGGGACTCAAACCCACAAACATTTAGTTAACAGCTAAACACCCTAGTCAAC-TGGCTTCAATCTAAAGCCCCGGCAGG-TTTGAAGCTGCTTCTTCGAATTTGCAATTCAATATGAAAT-TCACCTCGGAGCTTGGTAAAAAGAGGCCCAGCCTCTGTCTTTAGATTTACAGTCCAATGCCTTA-CTCAGCCATTTTACCACAAAAAAGGAAGGAATCGAACCCCCCAAAGCTGGTTTCAAGCCAACCCCATGACCTTCATGACTTTTTCAAAAGATATTAGAAAAACTATTTCATAACTTTGTCAAGGTTAAATTACGGGTT-AAACCCCGTATATCTTA-CACTGTAAAGCTAACCTAGCGTTAACCTTTTAAGTTAAAGATTAAGAGTATCGGCACCTCTTTGCAGTGA";

    private static int[] getStates(String sequence) {
        int[] states = new int[sequence.length()];

        for (int i = 0; i < sequence.length(); i++) {
            switch (sequence.charAt(i)) {
                case 'A':
                    states[i] = 0;
                    break;
                case 'C':
                    states[i] = 1;
                    break;
                case 'G':
                    states[i] = 2;
                    break;
                case 'T':
                    states[i] = 3;
                    break;
                default:
                    states[i] = 4;
                    break;
            }
        }
        return states;
    }

    private static double[] getPartials(String sequence) {
        double[] partials = new double[sequence.length() * 4];

        int k = 0;
        for (int i = 0; i < sequence.length(); i++) {
            switch (sequence.charAt(i)) {
                case 'A':
                    partials[k++] = 1;
                    partials[k++] = 0;
                    partials[k++] = 0;
                    partials[k++] = 0;
                    break;
                case 'C':
                    partials[k++] = 0;
                    partials[k++] = 1;
                    partials[k++] = 0;
                    partials[k++] = 0;
                    break;
                case 'G':
                    partials[k++] = 0;
                    partials[k++] = 0;
                    partials[k++] = 1;
                    partials[k++] = 0;
                    break;
                case 'T':
                    partials[k++] = 0;
                    partials[k++] = 0;
                    partials[k++] = 0;
                    partials[k++] = 1;
                    break;
                default:
                    partials[k++] = 1;
                    partials[k++] = 1;
                    partials[k++] = 1;
                    partials[k++] = 1;
                    break;
            }
        }
        return partials;
    }


    public static void main(String[] argv) {

        // is nucleotides...
        int stateCount = 4;

        // get the number of site patterns
        int nPatterns = human.length();

        PLLInfo.printVersionInformation();

        PLLInfo.printResourceList();

        System.setProperty("java.only", "true");

        // create an instance of the PLL library
        PLL instance = loadPLLInstance(
                3,				/**< Number of tip data elements (input) */
                5,	            /**< Number of partials buffers to create (input) */
                3,		        /**< Number of compact state representation buffers to create (input) */
                stateCount,		/**< Number of states in the continuous-time Markov chain (input) */
                nPatterns,		/**< Number of site patterns to be handled by the instance (input) */
                1,		        /**< Number of rate matrix eigen-decomposition buffers to allocate (input) */
                4,		        /**< Number of rate matrix buffers (input) */
                1,              /**< Number of rate categories (input) */
                3,               /**< Number of scale buffers (input) */
                new int[] {1, 0},
                0,
//                PLLFlag.PROCESSOR_GPU.getMask(),
                0
        );
        if (instance == null) {
            System.err.println("Failed to obtain PLL instance");
            System.exit(1);
        }

        StringBuilder sb = new StringBuilder();
        for (PLLFlag flag : PLLFlag.values()) {
            if (flag.isSet(instance.getDetails().getFlags())) {
                sb.append(" ").append(flag.name());
            }
        }
        System.out.println("Instance on resource #" + instance.getDetails().getResourceNumber() + " flags:" + sb.toString());

        double[] patternWeights = new double[nPatterns];
        for (int i = 0; i < nPatterns; i++) {
            patternWeights[i] = 1.0;
        }
        instance.setPatternWeights(patternWeights);

        instance.setTipStates(0, getStates(human));
        instance.setTipStates(1, getStates(chimp));
        instance.setTipStates(2, getStates(gorilla));

        // set the sequences for each tip using partial likelihood arrays
//        instance.setPartials(0, getPartials(human));
//        instance.setPartials(1, getPartials(chimp));
//        instance.setPartials(2, getPartials(gorilla));

        final double[] rates = { 1.0 };
        instance.setCategoryRates(rates);

        // create an array containing site category weights
        final double[] weights = { 1.0 };
        instance.setCategoryWeights(0, weights);

        // create base frequency array
        final double[] freqs = { 0.25, 0.25, 0.25, 0.25 };
        instance.setStateFrequencies(0, freqs);

        // an eigen decomposition for the JC69 model
        final double[] evec = {
                1.0,  2.0,  0.0,  0.5,
                1.0,  -2.0,  0.5,  0.0,
                1.0,  2.0, 0.0,  -0.5,
                1.0,  -2.0,  -0.5,  0.0
        };

        final double[] ivec = {
                0.25,  0.25,  0.25,  0.25,
                0.125,  -0.125,  0.125,  -0.125,
                0.0,  1.0,  0.0,  -1.0,
                1.0,  0.0,  -1.0,  0.0
        };

        double[] eval = { 0.0, -1.3333333333333333, -1.3333333333333333, -1.3333333333333333 };

        // set the Eigen decomposition
        instance.setEigenDecomposition(0, evec, ivec, eval);

        // a list of indices and edge lengths
        int[] nodeIndices = { 0, 1, 2, 3 };
        double[] edgeLengths = { 0.1, 0.1, 0.2, 0.1 };

        // tell PLL to populate the transition matrices for the above edge lengths
        instance.updateTransitionMatrices(
                0,             // eigenIndex
                nodeIndices,   // probabilityIndices
                null,          // firstDerivativeIndices
                null,          // secondDervativeIndices
                edgeLengths,   // edgeLengths
                4);            // count

        instance.resetScaleFactors(2);

        // create a list of partial likelihood update operations
        // the order is [dest, writeScale, readScale, source1, matrix1, source2, matrix2]
        int[] operations = {
                3, 0, 0, 0, 0, 1, 1,
                4, 1, 1, 2, 2, 3, 3
        };
        int[] rootIndices = { 4 };

        // update the partials
        instance.updatePartials(
                operations,     // eigenIndex
                2,              // operationCount
                2);             // rescale ?

        int[] scalingFactorsIndices = {2}; // internal nodes

        // TODO Need to call accumulateScaleFactors if scaling is enabled

        int[] weightIndices = { 0 };
        int[] freqIndices = { 0 };

        double[] sumLogLik = new double[1];

        // calculate the site likelihoods at the root node
        instance.calculateRootLogLikelihoods(
                rootIndices,            // bufferIndices
                weightIndices,                // weights
                freqIndices,                 // stateFrequencies
                scalingFactorsIndices,
                1,
                sumLogLik);         // outLogLikelihoods

        System.out.println("logL = " + sumLogLik[0] + " (PAUP logL = -1574.63623)");
    }

}
