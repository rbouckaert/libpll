/*
 * BeagleJNIjava
 *
 */

package pll;

import beast.core.util.Log;

/*
 * BeagleJNIjava
 *
 * @author Andrew Rambaut
 * @author Marc Suchard
 *
 */

public class PLLJNIImpl implements PLL {

    private int instance = -1;
    private InstanceDetails details = new InstanceDetails();

    public PLLJNIImpl(int tipCount,
                         int partialsBufferCount,
                         int compactBufferCount,
                         int stateCount,
                         int patternCount,
                         int eigenBufferCount,
                         int matrixBufferCount,
                         int categoryCount,
                         int scaleBufferCount,
                         final int[] resourceList,
                         long preferenceFlags,
                         long requirementFlags) {

        instance = PLLJNIWrapper.INSTANCE.createInstance(
        		tipCount,
                partialsBufferCount,
                stateCount,
                patternCount,
                categoryCount,
                matrixBufferCount,
                categoryCount,
                scaleBufferCount,
                flags);

        if (instance < 0) {
            details = null; // To communicate that no instance has been created!
            throw new PLLException("create", instance);
        }
    }

    public void finalize() throws Throwable {
        super.finalize();
        int errCode = PLLJNIWrapper.INSTANCE.finalize(instance);
        if (errCode != 0) {
            throw new PLLException("finalize", errCode);
        }
    }

    public void setPatternWeights(final int[] patternWeights) {
        int errCode = PLLJNIWrapper.INSTANCE.setPatternWeights(instance, patternWeights);
        if (errCode != 0) {
            throw new PLLException("setPatternWeights", errCode);
        }
    }

    public void setTipStates(int tipIndex, final int[] states) {
        int errCode = PLLJNIWrapper.INSTANCE.setTipStates(instance, tipIndex, states);
        if (errCode != 0) {
            throw new PLLException("setTipStates", errCode);
        }
    }

    public void getTipStates(int tipIndex, final int[] states) {
        int errCode = PLLJNIWrapper.INSTANCE.getTipStates(instance, tipIndex, states);
        if (errCode != 0) {
            throw new PLLException("getTipStates", errCode);
        }
    }

    public void setTipPartials(int tipIndex, final double[] partials) {
        int errCode = PLLJNIWrapper.INSTANCE.setTipPartials(instance, tipIndex, partials, 0);
        if (errCode != 0) {
            throw new PLLException("setTipPartials", errCode);
        }
    }

    public void setPartials(int bufferIndex, final double[] partials) {
        int errCode = PLLJNIWrapper.INSTANCE.setTipPartials(instance, bufferIndex, partials, 0);
        if (errCode != 0) {
            throw new PLLException("setPartials", errCode);
        }
    }

    public void getPartials(int bufferIndex, int scaleIndex, final double []outPartials) {
        int errCode = PLLJNIWrapper.INSTANCE.getPartials(instance, bufferIndex, scaleIndex, outPartials);
        if (errCode != 0) {
            throw new PLLException("getPartials", errCode);
        }
    }
    
    public void getLogScaleFactors(int scaleIndex, final double[] outFactors) {
        int errCode = PLLJNIWrapper.INSTANCE.getLogScaleFactors(instance, scaleIndex, outFactors);
        if (errCode != 0) {
            throw new PLLException("getScaleFactors", errCode);
        }
    }

    public void setEigenDecomposition(int eigenIndex,
                                      final double[] eigenVectors,
                                      final double[] inverseEigenValues,
                                      final double[] eigenValues) {
        int errCode = PLLJNIWrapper.INSTANCE.setEigenDecomposition(instance, eigenIndex, eigenVectors, inverseEigenValues, eigenValues);
        if (errCode != 0) {
            throw new PLLException("setEigenDecomposition", errCode);
        }
    }

    public void setStateFrequencies(int stateFrequenciesIndex,
                                    final double[] stateFrequencies) {
        int errCode = PLLJNIWrapper.INSTANCE.setStateFrequencies(instance,
                stateFrequenciesIndex, stateFrequencies);
        if (errCode != 0) {
            throw new PLLException("setStateFrequencies", errCode);
        }
    }

    public void setCategoryWeights( int categoryWeightsIndex,
                                    final double[] categoryWeights) {
    	if (categoryWeightsIndex != 0) {
    		Log.warning("categoryWeightsIndex = " + categoryWeightsIndex + "(expected 0)");
    	}
        int errCode = PLLJNIWrapper.INSTANCE.setCategoryWeights(instance,
                categoryWeights);
        if (errCode != 0) {
            throw new PLLException("setCategoryWeights", errCode);
        }
    }

    public void setCategoryRates(double[] inCategoryRates) {
        int errCode = PLLJNIWrapper.INSTANCE.setCategoryRates(instance, inCategoryRates);
        if (errCode != 0) {
            throw new PLLException("setCategoryRates", errCode);
        }
    }

    public void setTransitionMatrix(int matrixIndex, final double[] inMatrix, double paddedValue) {
        int errCode = PLLJNIWrapper.INSTANCE.setTransitionMatrix(instance, matrixIndex, inMatrix, paddedValue);
        if (errCode != 0) {
            throw new PLLException("setTransitionMatrix", errCode);
        }
    }

    public void getTransitionMatrix(int matrixIndex, final double[] outMatrix) {
        int errCode = PLLJNIWrapper.INSTANCE.getTransitionMatrix(instance, matrixIndex, outMatrix);
        if (errCode != 0) {
            throw new PLLException("getTransitionMatrix", errCode);
        }
    }

	// /////////////////////////
	// ---TODO: Epoch model---//
	// /////////////////////////

	public void convolveTransitionMatrices(final int[] firstIndices, 
                                           final int[] secondIndices,
                                           final int[] resultIndices, 
                                           int matrixCount) {

        int errCode = PLLJNIWrapper.INSTANCE.convolveTransitionMatrices(instance,
                                                                           firstIndices, 
                                                                           secondIndices,
                                                                           resultIndices, 
                                                                           matrixCount);
        if (errCode != 0) {
            throw new PLLException("convolveTransitionMatrices", errCode);
        }
		
	}//END: convolveTransitionMatrices    
    
    public void updateTransitionMatrices(int eigenIndex,
                                         final int[] probabilityIndices,
                                         final int[] firstDerivativeIndices,
                                         final int[] secondDervativeIndices,
                                         final double[] edgeLengths,
                                         int count) {
        int errCode = PLLJNIWrapper.INSTANCE.updateTransitionMatrices(instance,
                /*eigenIndex,*/ probabilityIndices,
                firstDerivativeIndices, secondDervativeIndices,
                edgeLengths, count);
        if (errCode != 0) {
            throw new PLLException("updateTransitionMatrices", errCode);
        }
    }


    public void updatePartials(final int[] operations, final int operationCount, final int cumulativeScaleIndex) {
        int errCode = PLLJNIWrapper.INSTANCE.updatePartials(instance, operations, operationCount, cumulativeScaleIndex);
        if (errCode != 0) {
            throw new PLLException("updatePartials", errCode);
        }
    }

    public void accumulateScaleFactors(final int[] scaleIndices, final int count, final int cumulativeScaleIndex) {
        int errCode = PLLJNIWrapper.INSTANCE.accumulateScaleFactors(instance, scaleIndices, count, cumulativeScaleIndex);
        if (errCode != 0) {
            throw new PLLException("accumulateScaleFactors", errCode);
        }
    }

    public void removeScaleFactors(int[] scaleIndices, int count, int cumulativeScaleIndex) {
        int errCode = PLLJNIWrapper.INSTANCE.removeScaleFactors(instance, scaleIndices, count, cumulativeScaleIndex);
        if (errCode != 0) {
            throw new PLLException("removeScaleFactors", errCode);
        }
    }

    public void copyScaleFactors(int destScalingIndex, int srcScalingIndex) {
        int errCode = PLLJNIWrapper.INSTANCE.copyScaleFactors(instance, destScalingIndex, srcScalingIndex);
        if (errCode != 0) {
            throw new PLLException("copyScaleFactors", errCode);
        }
    }

    public void resetScaleFactors(int cumulativeScaleIndex) {
        int errCode = PLLJNIWrapper.INSTANCE.resetScaleFactors(instance, cumulativeScaleIndex);
        if (errCode != 0) {
            throw new PLLException("resetScaleFactors", errCode);
        }
    }

    public void calculateRootLogLikelihoods(int[] bufferIndices,
                                            final int[] categoryWeightsIndices,
                                            final int[] stateFrequenciesIndices,
                                            final int[] cumulativeScaleIndices,
                                            int count,
                                            final double[] outSumLogLikelihood) {
        int errCode = PLLJNIWrapper.INSTANCE.calculateRootLogLikelihoods(instance,
                bufferIndices,
                categoryWeightsIndices,
                stateFrequenciesIndices,
                cumulativeScaleIndices,
                count,
                outSumLogLikelihood);
        // We probably don't want the Floating Point error to throw an exception...
        if (errCode != 0 && errCode != PLLErrorCode.FLOATING_POINT_ERROR.getErrCode()) {
            throw new PLLException("calculateRootLogLikelihoods", errCode);
        }
    }

    public void calculateEdgeLogLikelihoods(final int[] parentBufferIndices,
                                            final int[] childBufferIndices,
                                            final int[] probabilityIndices,
                                            final int[] firstDerivativeIndices,
                                            final int[] secondDerivativeIndices,
                                            final int[] categoryWeightsIndices,
                                            final int[] stateFrequenciesIndices,
                                            final int[] cumulativeScaleIndices,
                                            int count,
                                            final double[] outSumLogLikelihood,
                                            final double[] outSumFirstDerivative,
                                            final double[] outSumSecondDerivative) {
        int errCode = PLLJNIWrapper.INSTANCE.calculateEdgeLogLikelihoods(instance,
                parentBufferIndices,
                childBufferIndices,
                probabilityIndices,
                firstDerivativeIndices,
                secondDerivativeIndices,
                categoryWeightsIndices,
                stateFrequenciesIndices,
                cumulativeScaleIndices,
                count,
                outSumLogLikelihood,
                outSumFirstDerivative,
                outSumSecondDerivative);
        if (errCode != 0) {
            throw new PLLException("calculateEdgeLogLikelihoods", errCode);
        }
    }

    public void getSiteLogLikelihoods(final double[] outLogLikelihoods) {
        int errCode = PLLJNIWrapper.INSTANCE.getSiteLogLikelihoods(instance,
                outLogLikelihoods);
        if (errCode != 0) {
            throw new PLLException("getSiteLogLikelihoods", errCode);
        }
    }

    public InstanceDetails getDetails() {
        return details;
    }
}