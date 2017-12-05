#ifdef _WIN32
#include "libpll/JNI/winjni.h"
#endif

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <jni.h>

#include "src/pll.h"
#include "pll_PLLJNIWrapper.h"


static struct pll_partition ** partitions = NULL;
static int lastpartition = 0;
static int partitionSize = 0;


/*
 * Class:     pll_PLLJNIWrapper
 * Method:    getVersion
 * Signature: ()Ljava/lang/String {
}
 */
JNIEXPORT jstring JNICALL Java_pll_PLLJNIWrapper_getVersion
  (JNIEnv * env, jobject obj) {
  pll_hardware_dump();
      return (*env)->NewStringUTF(env, "1.0");
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    getCitation
 * Signature: ()Ljava/lang/String {
}
 */
JNIEXPORT jstring JNICALL Java_pll_PLLJNIWrapper_getCitation
  (JNIEnv * env, jobject obj) {
      return (*env)->NewStringUTF(env, "Flouri T., Izquierdo-Carrasco F., Darriba D., Aberer AJ, Nguyen LT, Minh BQ, von Haeseler A., Stamatakis A. (2015) The Phylogenetic Likelihood Library. Systematic Biology, 64(env, 2): 356-362. doi:10.1093/sysbio/syu084");
}


/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_partition_create
 * Signature: (SSSSSSSSS)I
 */

JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_createInstance
  (JNIEnv * env, jobject obj, jint tips, jint clv_buffers, jint states, jint sites, jint rate_matrices, jint prob_matrices, 
  jint rate_cats, jint scale_buffers, jint attributes) {

  if (partitions == NULL) {
  	partitions = (struct pll_partition **) malloc(sizeof(struct pll_partition *) * 100);
  	partitionSize = 100;
  	lastpartition = 0;
  }
  if (lastpartition == partitionSize) {
  	struct pll_partition ** tmp = (struct pll_partition **) malloc(sizeof(struct pll_partition *) * (partitionSize +100));
  	for (int i = 0; i < partitionSize; i++) {
  		tmp[i] = partitions[i];
  	}
  	free(partitions);
  	partitions = tmp;  	
  	partitionSize += 100;
  }
  partitions[lastpartition] = pll_partition_create(tips,
                                                   clv_buffers,
                                                   states,
                                                   sites,
                                                	rate_matrices,
                                                    prob_matrices,
                                                    rate_cats,
                                                    scale_buffers,
                                                    attributes);
                   

#define PLL_ATTRIB_PATTERN_TIP    (1 << 4)
                                                    
  printf("Creating pll partition using ");
  if ((attributes & PLL_ATTRIB_ARCH_MASK) == PLL_ATTRIB_ARCH_CPU) {printf("cpu");}
  if ((attributes & PLL_ATTRIB_ARCH_MASK) == PLL_ATTRIB_ARCH_SSE) {printf("sse");}
  if ((attributes & PLL_ATTRIB_ARCH_MASK) == PLL_ATTRIB_ARCH_AVX) {printf("avx");}
  if ((attributes & PLL_ATTRIB_ARCH_MASK) == PLL_ATTRIB_ARCH_AVX2) {printf("avx2");}
  if ((attributes & PLL_ATTRIB_ARCH_MASK) == PLL_ATTRIB_ARCH_AVX512) {printf("avx512");}
  if (attributes & PLL_ATTRIB_PATTERN_TIP) {printf(" with tip states");} else {printf(" with tip partials");}
  printf("\n");
  fflush(stdout);
  lastpartition++;
  
  return lastpartition - 1;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    finalize
 * Signature: (I)V
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_finalize
  (JNIEnv * env, jobject obj, jint partition) {
  pll_partition_destroy(partitions[partition]);
  partitions[partition] = NULL;  
  jint error = 0;
  return error;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_set_tip_states
 * Signature: (IS[S[C)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setTipStates
  (JNIEnv * env, jobject obj, jint partition, jint tip_index, jintArray in_map, jstring in_sequence) {

	jint * map = (*env)->GetIntArrayElements(env, in_map, NULL);
	const char *sequence = (*env)->GetStringUTFChars(env, in_sequence, 0);

	int r = pll_set_tip_states(partitions[partition], tip_index, (unsigned int *) map, sequence);
	return r;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_set_tip_clv
 * Signature: (IS[DI)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setTipPartials
  (JNIEnv * env, jobject obj, jint partition, jint tip_index, jdoubleArray in_clv, jint padding) {
	jdouble * clv = (*env)->GetDoubleArrayElements(env, in_clv, NULL);

	int r = pll_set_tip_clv(partitions[partition], tip_index, clv, padding);
	return r;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setPatternWeights
 * Signature: (I[S)V
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setPatternWeights
  (JNIEnv * env, jobject obj, jint partition, jintArray in_pattern_weights) {
  jint * pattern_weights = (*env)->GetIntArrayElements(env, in_pattern_weights, NULL);
  pll_set_pattern_weights(partitions[partition], (unsigned int *) pattern_weights);
  jint error = 0;
  return error;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_set_asc_bias_type
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_pll_1set_1asc_1bias_1type
  (JNIEnv * env, jobject obj, jint partition, jint asc_bias_type) {
  // TODO
  return 0;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_set_asc_state_weights
 * Signature: (I[S)V
 */
JNIEXPORT void JNICALL Java_pll_PLLJNIWrapper_pll_1set_1asc_1state_1weights
  (JNIEnv * env, jobject obj, jint partition, jintArray in_state_weights) {
  // TODO
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setTransitionMatrix
 * Signature: (II[D)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setTransitionMatrix
  (JNIEnv * env, jobject obj, jint partition, jint params_index, jdoubleArray in_params) {
  jdouble * params = (*env)->GetDoubleArrayElements(env, in_params, NULL);
  pll_set_subst_params(partitions[partition], params_index, params);
  jint error = 0;
  return error;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setStateFrequencies
 * Signature: (IS[D)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setStateFrequencies
  (JNIEnv * env, jobject obj, jint partition, jint params_index, jdoubleArray in_frequencies) {
  jdouble * frequencies = (*env)->GetDoubleArrayElements(env, in_frequencies, NULL);
  pll_set_frequencies(partitions[partition], params_index, frequencies);
  jint error = 0;
  return error;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setCategoryRates
 * Signature: (I[D)V
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setCategoryRates
  (JNIEnv * env, jobject obj, jint partition, jdoubleArray in_rates) {
  jdouble * rates = (*env)->GetDoubleArrayElements(env, in_rates, NULL);
  pll_set_category_rates(partitions[partition], rates);
  jint error = 0;
  return error;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setCategoryWeights
 * Signature: (I[D)V
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setCategoryWeights
  (JNIEnv * env, jobject obj, jint partition, jdoubleArray in_rate_weights) {
  jdouble * rate_weights = (*env)->GetDoubleArrayElements(env, in_rate_weights, NULL);
  pll_set_category_weights(partitions[partition], rate_weights);
  jint error = 0;
  return error;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_update_eigen
 * Signature: (IS)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_pll_1update_1eigen
  (JNIEnv * env, jobject obj, jint partition, jshort params_index) {
  jint r = pll_update_eigen(partitions[partition], params_index);
  return r;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    updateTransitionMatrices
 * Signature: (I[S[S[DS)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_updateTransitionMatrices
  (JNIEnv * env, jobject obj, jint partition, jint in_params_index, jintArray in_matrix_indices, jdoubleArray in_branch_lengths, jint count) {
  
  // fprintf(stderr,"rate_cats = %d\n", partitions[partition]->rate_cats); 
  unsigned int * params_index = (unsigned int *) malloc(sizeof(unsigned int) * partitions[partition]->rate_cats);
  for (unsigned int i = 0; i < partitions[partition]->rate_cats; i++) {
  	params_index[i] = in_params_index;
  }
  jint * matrix_indices = (*env)->GetIntArrayElements(env, in_matrix_indices, NULL);
  jdouble * branch_lengths = (*env)->GetDoubleArrayElements(env, in_branch_lengths, NULL);
  jint r = pll_update_prob_matrices(partitions[partition], (unsigned int *) params_index, (unsigned int *) matrix_indices, branch_lengths, count);
  free(params_index);
  return r;
}


/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_count_invariant_sites
 * Signature: (I[S)S
 */
JNIEXPORT jshort JNICALL Java_pll_PLLJNIWrapper_pll_1count_1invariant_1sites
  (JNIEnv * env, jobject obj, jint partition, jintArray in_state_inv_count) {
  jint * state_inv_count = (*env)->GetIntArrayElements(env, in_state_inv_count, NULL);
  jshort r = pll_count_invariant_sites(partitions[partition], (unsigned int *) state_inv_count);
  return r;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_update_invariant_sites
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_pll_1update_1invariant_1sites
  (JNIEnv * env, jobject obj, jint partition) {
  jint r = pll_update_invariant_sites(partitions[partition]);
  return r;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_update_invariant_sites_proportion
 * Signature: (ISD)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_pll_1update_1invariant_1sites_1proportion
  (JNIEnv * env, jobject obj, jint partition, jint params_index, jdouble prop_invar) {
  jint r = pll_update_invariant_sites_proportion(partitions[partition], params_index, prop_invar);
  return r;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_compute_root_loglikelihood
 * Signature: (ISI[S[D)D
 */
JNIEXPORT jdouble JNICALL Java_pll_PLLJNIWrapper_pll_1compute_1root_1loglikelihood
  (JNIEnv * env, jobject obj, jint partition, jint clv_index, jint scaler_index, 
	  jintArray in_freqs_indices, jdoubleArray in_persite_lnl) {
  jint * freqs_indices = (*env)->GetIntArrayElements(env, in_freqs_indices, NULL);
  jdouble * persite_lnl = 
	  in_persite_lnl == NULL ? NULL : (*env)->GetDoubleArrayElements(env, in_persite_lnl, NULL);
  jdouble logP = pll_compute_root_loglikelihood(partitions[partition], clv_index,
		scaler_index, (unsigned int *) freqs_indices, persite_lnl);
	return logP;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_compute_edge_loglikelihood
 * Signature: (ISISIS[S[D)D
 */
JNIEXPORT jdouble JNICALL Java_pll_PLLJNIWrapper_pll_1compute_1edge_1loglikelihood
  (JNIEnv * env, jobject obj, jint partition, jshort x1, jint x2, jshort x3, jint x4, jshort x5, jintArray x6, jdoubleArray x7) {
  // TODO
  return 0.0;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    updatePartials
 * Signature: (I[SS)V
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_updatePartials
  (JNIEnv * env, jobject obj, jint partition, jintArray in_operations, jint count) {
  jint * operationsArray = (*env)->GetIntArrayElements(env, in_operations, NULL);
  
  
  struct pll_operation * operations = (struct pll_operation *) malloc( count * sizeof (struct pll_operation));
  int k = 0;
  for (int i = 0; i < count; i++) {
  operations[i].parent_clv_index = operationsArray[k++];
  operations[i].parent_scaler_index = operationsArray[k++];
  operations[i].child1_clv_index = operationsArray[k++];
  operations[i].child2_clv_index = operationsArray[k++];
  operations[i].child1_matrix_index = operationsArray[k++];
  operations[i].child2_matrix_index = operationsArray[k++];
  operations[i].child1_scaler_index = operationsArray[k++];
  operations[i].child2_scaler_index = operationsArray[k++]; 
  
  /*
  printf("operation[%d] = (%u,%d,%u,%u,%d,%u,%u,%d)\n", i,
	  operations[i].parent_clv_index,
	  operations[i].parent_scaler_index,
	  operations[i].child1_clv_index,
	  operations[i].child1_matrix_index,
	  operations[i].child1_scaler_index,
	  operations[i].child2_clv_index,
	  operations[i].child2_matrix_index,
	  operations[i].child2_scaler_index);
	  */
  }
  pll_update_partials(partitions[partition], operations, count);
  free(operations);
  jint error = 0;
  return error;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_update_sumtable
 * Signature: (ISSII[S[D)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_pll_1update_1sumtable
  (JNIEnv * env, jobject obj, jint partition, jshort x1, jshort x2, jint x3, jint x4, jintArray x5, jdoubleArray x6) {
  // TODO
  return 0;
}

/*
 * Class:     pll_PLLJNIWrapper
 * Method:    pll_compute_likelihood_derivatives
 * Signature: (IIID[S[D[D[D)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_pll_1compute_1likelihood_1derivatives
  (JNIEnv * env, jobject obj, jint partition, jint x1, jint x2, jdouble x3, jintArray x4, jdoubleArray x5, jdoubleArray x6, jdoubleArray x7) {
  // TODO
  return 0;
}


/*
 * Class:     pll_PLLJNIWrapper
 * Method:    showMmatrix
 * Signature: (III)V
 */
JNIEXPORT void JNICALL Java_pll_PLLJNIWrapper_showMmatrix
  (JNIEnv * env, jobject obj, jint partition, jint index, jint floatPrecision) {
  pll_show_pmatrix(partitions[partition], index, floatPrecision);
  fflush(stdout);
}

/* Class:     pll_PLLJNIWrapper
 * Method:    showClv
 * Signature: (IIII)V
 */
JNIEXPORT void JNICALL Java_pll_PLLJNIWrapper_showClv
  (JNIEnv * env, jobject obj, jint partition, jint index, jint scalerIndex, jint floatPrecision) {
  pll_show_clv(partitions[partition], index, scalerIndex, floatPrecision);
  fflush(stdout);
}


/*
 * Class:     pll_PLLJNIWrapper
 * Method:    setEigenDecomposition
 * Signature: (II[D[D[D)I
 */
JNIEXPORT jint JNICALL Java_pll_PLLJNIWrapper_setEigenDecomposition
  (JNIEnv * env, jobject obj, jint partition, jint eigenIndex, jdoubleArray in_eigenvecs, jdoubleArray in_inv_eigenvecs, jdoubleArray in_eigenvals) {
  jdouble * eigenvecs = (*env)->GetDoubleArrayElements(env, in_eigenvecs, NULL);
  jdouble * inv_eigenvecs = (*env)->GetDoubleArrayElements(env, in_inv_eigenvecs, NULL);
  jdouble * eigenvals = (*env)->GetDoubleArrayElements(env, in_eigenvals, NULL);
  
  int states = partitions[partition]->states;
  partitions[partition]->eigen_decomp_valid[eigenIndex] = 1;
  memcpy(eigenvecs, partitions[partition]->eigenvecs, states * states);
  memcpy(inv_eigenvecs, partitions[partition]->inv_eigenvecs, states * states);
  memcpy(eigenvals, partitions[partition]->eigenvals, states);
  
  jint error = 0;
  return error;
  
}
