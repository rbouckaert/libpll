/*
 * BeagleJNIjava
 *
 */

package pll;


/*
 * BeagleJNIjava
 *
 * @author Andrew Rambaut
 * @author Marc Suchard
 *
 */

public class PLLJNIWrapper {

    public static final String LIBRARY_NAME = getPlatformSpecificLibraryName();

    /**
     * private final constructor to enforce singleton instance
     */
    private PLLJNIWrapper() {
    }

    public native String getVersion();

    public native String getCitation();

    public native ResourceDetails[] getResourceList();

//    public native int createInstance(
//            int tipCount,
//            int partialsBufferCount,
//            int compactBufferCount,
//            int stateCount,
//            int patternCount,
//            int eigenBufferCount,
//            int matrixBufferCount,
//            int categoryCount,
//            int scaleBufferCount,
//            final int[] resourceList,
//            int resourceCount,
//            long preferenceFlags,
//            long requirementFlags);
//    //, InstanceDetails returnInfo);
//
//    public native int finalize(int instance);

    /* functions in pll.c */

    native int createInstance(int tipCount,
    		int partialsBufferCount,
    		int stateCount,
    		int patternCount,
    		int matrixBufferCount,
    		int prob_matrices,
    		int categoryCount,
    		int scaleBufferCount,
    		int flags);

    native int finalize(int partition);

    native int setTipStates(int partition,
                                      int tip_index,
                                      final int [] map,
                                      final String sequence);


    native int setTipPartials(int partition,
                                   int tip_index,
                                   final double [] clv,
                                   int padding);
    native int setPartials(int partition,
            int clv_index,
            final double [] clv,
            int padding);

    native int setPatternWeights(int partition,
                                            final int [] pattern_weights);

    native int pll_set_asc_bias_type(int partition,
                                         int asc_bias_type);

    native void pll_set_asc_state_weights(int partition,
                                              final int [] state_weights);

    /* functions in list.c */

//    native int pll_dlist_append(pll_dlist_t ** dlist, void [] data);
//    native int pll_dlist_remove(pll_dlist_t ** dlist, void [] data);
//    native int pll_dlist_prepend(pll_dlist_t ** dlist, void [] data);

    /* functions in models.c */

    native int setTransitionMatrix(int partition,
                                         int params_index,
                                         final double [] params);

    native int setStateFrequencies(int partition,
                                        int params_index,
                                        final double [] frequencies);

    native int setCategoryRates(int partition,
                                           final double [] rates);

    native int setCategoryWeights(int partition,
                                             final double [] rate_weights);

    native int pll_update_eigen(int partition,
                                    short params_index);

    native int updateTransitionMatrices(int partition,
                                            final int [] params_index,
                                            final int [] matrix_indices,
                                            final double [] branch_lengths,
                                            int count);

    native short pll_count_invariant_sites(int partition,
                                                      int [] state_inv_count);

    native int pll_update_invariant_sites(int partition);

    native int pll_update_invariant_sites_proportion(int partition,
                                                         int params_index,
                                                         double prop_invar);

//    native void * pll_aligned_alloc(size_t size, size_t alignment);
//
//    native void pll_aligned_free(void * ptr);

    /* functions in likelihood.c */

    native double pll_compute_root_loglikelihood(int partition,
                                                     int clv_index,
                                                     int scaler_index,
                                                     final int [] freqs_indices,
                                                     double [] persite_lnl);

    native double pll_compute_edge_loglikelihood(int partition,
                                                     short parent_clv_index,
                                                     int parent_scaler_index,
                                                     short child_clv_index,
                                                     int child_scaler_index,
                                                     short matrix_index,
                                                     final int [] freqs_indices,
                                                     double [] persite_lnl);

    /* functions in partials.c */

    native void pll_update_partials(int partition,
                                        final /*pll_operation_t */ int [] operations,
                                        int count);


    /* functions in derivatives.c */

    native int pll_update_sumtable(int partition,
                                          short parent_clv_index,
                                          short child_clv_index,
                                          int parent_scaler_index,
                                          int child_scaler_index,
                                          final int [] params_indices,
                                          double []sumtable);

    native int pll_compute_likelihood_derivatives(int partition,
                                                      int parent_scaler_index,
                                                      int child_scaler_index,
                                                      double branch_length,
                                                      final int [] params_indices,
                                                      final double [] sumtable,
                                                      double [] d_f,
                                                      double [] dd_f);

    /* Library loading routines */

    private static String getPlatformSpecificLibraryName()
    {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        if (osName.startsWith("windows")) {
            if(osArch.equals("x86")||osArch.equals("i386")) return "pll";
            if(osArch.startsWith("amd64")||osArch.startsWith("x86_64")) return "pll";
        }
        return "pll";
    }

    public static void loadPLLLibrary() throws UnsatisfiedLinkError {
        String path = "";
        if (System.getProperty("pll.library.path") != null) {
            path = System.getProperty("pll.library.path");
            if (path.length() > 0 && !path.endsWith("/")) {
                path += "/";
            }
        }

        System.loadLibrary(path + LIBRARY_NAME);
        INSTANCE = new PLLJNIWrapper();
    }

    public static PLLJNIWrapper INSTANCE;
    
    
	native void showMmatrix(int partition, int index, int floatPrecision);

	native void showClv(int partition, int index, int scalerIndex, int floatPrecision);




}

