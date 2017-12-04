package pll;

import org.junit.Test;

import junit.framework.TestCase;

public class RootedExampleTest extends TestCase {

	/** replicate results from libpll/examples/rooted/rooted.c **/
	@Test
    public void testMain() {
    	PLLJNIWrapper.loadPLLLibrary();
    	System.out.println("v=" + PLLJNIWrapper.INSTANCE.getVersion());

    	  /* create the PLL partition PLLJNIWrapper.INSTANCE */
    	  int partition = PLLJNIWrapper.INSTANCE.createInstance(5,       /* How many tip sequences do we have */
    	                                   4,       /* How many extra CLV buffers (apart from the tip sequences) should we allocate */
    	                                   4,       /* How many states do our data have */
    	                                   6,       /* How long are the tip sequences (number of sites) */
    	                                   1,       /* How many different substitution models (or eigen decompositions) do we want to use concurrently (i.e. 4 for LG4) */
    	                                   5,       /* How many probability matrices should we allocate */
    	                                   4,       /* Number of rate categories */
    	                                   4,       /* How many scale buffers do we want */
    	                                   PLLFlag.PLL_ATTRIB_ARCH_AVX.getMask()
    	          //| PLLFlag.PLL_ATTRIB_PATTERN_TIP.getMask() 
    			  );        /* various attributes */

    	  /* initialize an array of two different branch lengths */
    	  double branch_lengths[] = { 0.36, 0.722, 0.985, 0.718, 1.44};

    	  /* initialize an array of frequencies */
    	  double frequencies[] = { 0.17, 0.19, 0.25, 0.39 };

    	  /* To be used together with branch_lengths to map branch lengths to
    	     probability matrices */
    	  int matrix_indices[] = { 0, 1, 2, 3, 4};

    	  /* substitution rates for the GTR model */
    	  double subst_params[] = {1,1,1,1,1,1};

    	  /* discretized category rates from a gamma distribution with alpha shape 1 */
    	  double rate_cats[] = {0.13695378267140107,
    	                         0.47675185617665189,
    	                         0.99999999997958422,
    	                         2.38629436117236260};
    	  /* set frequencies */
    	  PLLJNIWrapper.INSTANCE.setStateFrequencies(partition, 0, frequencies);

    	  /* set substitution parameters */
    	  PLLJNIWrapper.INSTANCE.setTransitionMatrix(partition, 0, subst_params);

    	  /* set rate categories */
    	  PLLJNIWrapper.INSTANCE.setCategoryRates(partition, rate_cats);
    	  
    	  /* set the 5 tip CLVs, and use the pll_map_nt map for converting
    	     the sequences to CLVs */
    	  int pll_map_nt[] =
    		  {
    		    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
    		    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
    		    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 15,  0,  0,
    		    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 15,
    		    0,  1, 14,  2, 13,  0,  0,  4, 11,  0,  0, 12,  0,  3, 15, 15,
    		    0,  0,  5,  6,  8,  8,  7,  9, 15, 10,  0,  0,  0,  0,  0,  0,
    		    0,  1, 14,  2, 13,  0,  0,  4, 11,  0,  0, 12,  0,  3, 15, 15,
    		    0,  0,  5,  6,  8,  8,  7,  9, 15, 10,  0,  0,  0,  0,  0,  0,
    		    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
    		    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
    		    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
    		    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
    		    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
    		    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
    		    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
    		    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
    		  };

    	  PLLJNIWrapper.INSTANCE.setTipStates(partition, 0, pll_map_nt, "WAAAAB");
    	  PLLJNIWrapper.INSTANCE.setTipStates(partition, 1, pll_map_nt, "CACACD");
    	  PLLJNIWrapper.INSTANCE.setTipStates(partition, 2, pll_map_nt, "AGGACA");
    	  PLLJNIWrapper.INSTANCE.setTipStates(partition, 3, pll_map_nt, "CGTAGT");
    	  PLLJNIWrapper.INSTANCE.setTipStates(partition, 4, pll_map_nt, "CGAATT");

    	  
    	  /* print out the CLVs at tip and inner nodes*/
  		  System.out.print ("Tip 0: ");
		  PLLJNIWrapper.INSTANCE.showClv(partition,0,PLLFlag.PLL_SCALE_BUFFER_NONE.getMask(),7);
		  System.out.print ("Tip 1: ");
		  PLLJNIWrapper.INSTANCE.showClv(partition,1,PLLFlag.PLL_SCALE_BUFFER_NONE.getMask(),7);
		  System.out.print ("Tip 2: ");
		  PLLJNIWrapper.INSTANCE.showClv(partition,2,PLLFlag.PLL_SCALE_BUFFER_NONE.getMask(),7);
		  System.out.print ("Tip 3: ");
		  PLLJNIWrapper.INSTANCE.showClv(partition,3,PLLFlag.PLL_SCALE_BUFFER_NONE.getMask(),7);
		  System.out.print ("Tip 4: ");
		  PLLJNIWrapper.INSTANCE.showClv(partition,4,PLLFlag.PLL_SCALE_BUFFER_NONE.getMask(),7);

    	  /* update five probability matrices using the rate matrix with
    	     index 0. The i-th matrix (i ranges from 0 to matrix_count - 1) is
    	     generated using branch length branch_lengths[i] and rate matrix
    	     (substitution rates + frequencies) params_indices[i], and can be refered
    	     to with index matrix_indices[i] */

    	  int params_indices[] = {0,0,0,0};

    	  PLLJNIWrapper.INSTANCE.updateTransitionMatrices(partition,
    	                           params_indices,
    	                           matrix_indices,
    	                           branch_lengths,
    	                           5);

    	  /* output the two probability matrices (for each rate category) on screen */
    	  for (int i = 0; i < 5; ++i)
    	  {
    	    System.out.println("P-matrix for branch length " + branch_lengths[i]);
    	    PLLJNIWrapper.INSTANCE.showMmatrix(partition, i, 7);
    	    System.out.println();
    	  }

    	  /* create an operations array for specifying the traversal
    	     descriptor when computing the CLVs */
    	  int [] operations = new int[4 * 8];
    	  int k = 0;
    	  operations[k++] = 5;
    	  operations[k++] = 0;
    	  operations[k++] = 0;
    	  operations[k++] = 1;
    	  operations[k++] = 0;
    	  operations[k++] = 0;
    	  operations[k++] = PLLFlag.PLL_SCALE_BUFFER_NONE.getMask();
    	  operations[k++] = PLLFlag.PLL_SCALE_BUFFER_NONE.getMask();

    	  operations[k++] = 6;
    	  operations[k++] = 1;
    	  operations[k++] = 5;
    	  operations[k++] = 2;
    	  operations[k++] = 1;
    	  operations[k++] = 2;
    	  operations[k++] = 0;
    	  operations[k++] = PLLFlag.PLL_SCALE_BUFFER_NONE.getMask();

    	  operations[k++] = 7;
    	  operations[k++] = 2;
    	  operations[k++] = 3;
    	  operations[k++] = 4;
    	  operations[k++] = 0;
    	  operations[k++] = 0;
    	  operations[k++] = PLLFlag.PLL_SCALE_BUFFER_NONE.getMask();
    	  operations[k++] = PLLFlag.PLL_SCALE_BUFFER_NONE.getMask();

    	  operations[k++] = 8;
    	  operations[k++] = 3;
    	  operations[k++] = 6;
    	  operations[k++] = 7;
    	  operations[k++] = 3;
    	  operations[k++] = 4;
    	  operations[k++] = 1;
    	  operations[k++] = 2;

    	  /* use the operations array to compute 4 CLVs. Operations will be carried out
    	     starting from operation 0 to 3 */
    	  PLLJNIWrapper.INSTANCE.pll_update_partials(partition, operations, 4);    	  

    	  /* print out the CLVs at tip and inner nodes*/
    	  System.out.print ("CLV 5: ");
    	  PLLJNIWrapper.INSTANCE.showClv(partition,5,0,7);
    	  System.out.print ("CLV 6: ");
    	  PLLJNIWrapper.INSTANCE.showClv(partition,6,1,7);
    	  System.out.print ("CLV 7: ");
    	  PLLJNIWrapper.INSTANCE.showClv(partition,7,2,7);
    	  System.out.print ("CLV 8: ");
    	  PLLJNIWrapper.INSTANCE.showClv(partition,8,3,7);

    	  /* compute the likelihood at the root of the rooted tree by specifying the CLV
    	     index of the root CLV and the index of the frequency vector to be used */
    	  double logl = PLLJNIWrapper.INSTANCE.pll_compute_root_loglikelihood(partition, 8, 3, params_indices, null);

      	  // Log-L: -37.947089    	  
    	  System.out.println("Log-L: " + logl);
    	  assertEquals(-37.947089, logl, 1e-6);

    	  
    	  
    	  /* What if we want to consider invariant sites? Let's first update the
    	     partition with information about which sites are invariant */
    	  PLLJNIWrapper.INSTANCE.pll_update_invariant_sites(partition);

    	  /* Now let's set the log-likelihood proportion that
    	     invariant sites affect to 0.5 */
    	  PLLJNIWrapper.INSTANCE.pll_update_invariant_sites_proportion(partition, 0, 0.5);

    	  /* we need to update the probability matrices after stating that we want
    	     to use invariant sites */
    	  PLLJNIWrapper.INSTANCE.updateTransitionMatrices(partition,
    	                           params_indices,
    	                           matrix_indices,
    	                           branch_lengths,
    	                           5);

    	  /* recompute the CLVs using the same traversal */
    	  PLLJNIWrapper.INSTANCE.pll_update_partials(partition, operations, 4);

    	  /* re-evaluate the log-likelihood */
    	  logl = PLLJNIWrapper.INSTANCE.pll_compute_root_loglikelihood(partition,8,3,params_indices, null);

    	  // Log-L (Inv+Gamma 0.5): -39.273468
    	  System.out.println("Log-L (Inv+Gamma 0.5): " + logl);
    	  assertEquals(-39.273468, logl, 1e-6);
    	  
    	  
    	  /* Let's assume now we want to use a proportion of 0.75 for invariants. Since
    	     tip states haven't changed, we should only update the proportion and
    	     then update the probability matrices */
    	  PLLJNIWrapper.INSTANCE.pll_update_invariant_sites_proportion(partition, 0, 0.75);
    	  PLLJNIWrapper.INSTANCE.updateTransitionMatrices(partition,
    	                           params_indices,
    	                           matrix_indices,
    	                           branch_lengths,
    	                           5);

    	  /* recompute the CLVs using the same traversal */
    	  PLLJNIWrapper.INSTANCE.pll_update_partials(partition, operations, 4);

    	  /* re-evaluate the log-likelihood */
    	  logl = PLLJNIWrapper.INSTANCE.pll_compute_root_loglikelihood(partition,8,3,params_indices,null);

    	  // Log-L (Inv+Gamma 0.75): -42.174311
    	  System.out.println("Log-L (Inv+Gamma 0.75):" + logl);
    	  assertEquals(-42.174311, logl, 1e-6);
    	  PLLJNIWrapper.INSTANCE.finalize(partition);
	}
    
}
