/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class pll_PLLJNIWrapper */

#ifndef _Included_pll_PLLJNIWrapper
#define _Included_pll_PLLJNIWrapper
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     pll_PLLJNIWrapper
 * Method:    getVersion
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_pll_PLLJNIWrapper_getVersion
  (JNIEnv *, jobject);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    getCitation
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_pll_PLLJNIWrapper_getCitation
  (JNIEnv *, jobject);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    getResourceList
 * Signature: ()[Lpll/ResourceDetails;
 */
JNIEXPORT jobjectArray JNICALL Java_pll_PLLJNIWrapper_getResourceList
  (JNIEnv *, jobject);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    createInstance
 * Signature: (IIIIIIIII)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_createInstance
  (JNIEnv *, jobject, jint, jint, jint, jint, jint, jint, jint, jint, jint);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    finalize
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_finalize
  (JNIEnv *, jobject, jint);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setTipStates
 * Signature: (II[ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setTipStates
  (JNIEnv *, jobject, jint, jint, jintArray, jstring);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setTipPartials
 * Signature: (II[DI)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setTipPartials
  (JNIEnv *, jobject, jint, jint, jdoubleArray, jint);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setPartials
 * Signature: (II[DI)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setPartials
  (JNIEnv *, jobject, jint, jint, jdoubleArray, jint);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setPatternWeights
 * Signature: (I[I)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setPatternWeights
  (JNIEnv *, jobject, jint, jintArray);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_set_asc_bias_type
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_pll_1set_1asc_1bias_1type
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_set_asc_state_weights
 * Signature: (I[I)V
 */
JNIEXPORT void JNICALL Java_pll_PLLJNIWrapper_pll_1set_1asc_1state_1weights
  (JNIEnv *, jobject, jint, jintArray);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setTransitionMatrix
 * Signature: (II[D)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setTransitionMatrix
  (JNIEnv *, jobject, jint, jint, jdoubleArray);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setStateFrequencies
 * Signature: (II[D)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setStateFrequencies
  (JNIEnv *, jobject, jint, jint, jdoubleArray);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setCategoryRates
 * Signature: (I[D)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setCategoryRates
  (JNIEnv *, jobject, jint, jdoubleArray);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setCategoryWeights
 * Signature: (I[D)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setCategoryWeights
  (JNIEnv *, jobject, jint, jdoubleArray);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_update_eigen
 * Signature: (IS)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_pll_1update_1eigen
  (JNIEnv *, jobject, jint, jshort);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    updateTransitionMatrices
 * Signature: (II[I[DI)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_updateTransitionMatrices
  (JNIEnv *, jobject, jint, jint, jintArray, jdoubleArray, jint);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_count_invariant_sites
 * Signature: (I[I)S
 */
JNIEXPORT jshort JNICALL Java_pll_PLLJNIWrapper_pll_1count_1invariant_1sites
  (JNIEnv *, jobject, jint, jintArray);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_update_invariant_sites
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_pll_1update_1invariant_1sites
  (JNIEnv *, jobject, jint);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_update_invariant_sites_proportion
 * Signature: (IID)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_pll_1update_1invariant_1sites_1proportion
  (JNIEnv *, jobject, jint, jint, jdouble);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_compute_root_loglikelihood
 * Signature: (III[I[D)D
 */
JNIEXPORT jdouble JNICALL Java_pll_PLLJNIWrapper_pll_1compute_1root_1loglikelihood
  (JNIEnv *, jobject, jint, jint, jint, jintArray, jdoubleArray);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_compute_edge_loglikelihood
 * Signature: (ISISIS[I[D)D
 */
JNIEXPORT jdouble JNICALL Java_pll_PLLJNIWrapper_pll_1compute_1edge_1loglikelihood
  (JNIEnv *, jobject, jint, jshort, jint, jshort, jint, jshort, jintArray, jdoubleArray);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    updatePartials
 * Signature: (I[II)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_updatePartials
  (JNIEnv *, jobject, jint, jintArray, jint);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_update_sumtable
 * Signature: (ISSII[I[D)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_pll_1update_1sumtable
  (JNIEnv *, jobject, jint, jshort, jshort, jint, jint, jintArray, jdoubleArray);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_compute_likelihood_derivatives
 * Signature: (IIID[I[D[D[D)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_pll_1compute_1likelihood_1derivatives
  (JNIEnv *, jobject, jint, jint, jint, jdouble, jintArray, jdoubleArray, jdoubleArray, jdoubleArray);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    showMmatrix
 * Signature: (III)V
 */
JNIEXPORT void JNICALL Java_pll_PLLJNIWrapper_showMmatrix
  (JNIEnv *, jobject, jint, jint, jint);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    showClv
 * Signature: (IIII)V
 */
JNIEXPORT void JNICALL Java_pll_PLLJNIWrapper_showClv
  (JNIEnv *, jobject, jint, jint, jint, jint);

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setEigenDecomposition
 * Signature: (II[D[D[D)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setEigenDecomposition
  (JNIEnv *, jobject, jint, jint, jdoubleArray, jdoubleArray, jdoubleArray);

#ifdef __cplusplus
}
#endif
#endif
