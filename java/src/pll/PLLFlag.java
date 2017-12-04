package pll;

/**
 * @author Andrew Rambaut
 * @author Marc Suchard
 * @version $Id$
 */
public enum PLLFlag {
    PRECISION_SINGLE(1 << 0, "double precision computation"),
    PRECISION_DOUBLE(1 << 1, "single precision computation"),

    COMPUTATION_SYNCH(1 << 2, "synchronous computation (blocking"),
    COMPUTATION_ASYNCH(1 << 3, "asynchronous computation (non-blocking)"),

    EIGEN_REAL(1 <<4, "real eigenvalue computation"),
    EIGEN_COMPLEX(1 <<5, "complex eigenvalue computation"),

    SCALING_MANUAL(1 << 6, "manual scaling"),
    SCALING_AUTO(1 << 7, "auto-scaling on"),
    SCALING_ALWAYS(1 << 8, "scale at every update"),
    SCALING_DYNAMIC(1 << 19, "manual scaling with dynamic checking"),            

    SCALERS_RAW(1 << 9, "save raw scalers"),
    SCALERS_LOG(1 << 10, "save log scalers"),

    VECTOR_SSE(1 << 11, "SSE vector computation"),
    VECTOR_NONE(1 << 12, "no vector computation"),

    THREADING_OPENMP(1 << 13, "OpenMP threading"),
    THREADING_NONE(1 << 14, "no threading"),

    PROCESSOR_CPU(1 << 15, "use CPU as main processor"),
    PROCESSOR_GPU(1 << 16, "use GPU as main processor"),
    PROCESSOR_FPGA(1 << 17, "use FPGA as main processor"),
    PROCESSOR_CELL(1 << 18, "use CELL as main processor"),

    PLL_ATTRIB_ARCH_CPU(0, "use CPU implementation"),
    PLL_ATTRIB_ARCH_SSE(1 << 0, "use SSE instructions"),
    PLL_ATTRIB_ARCH_AVX(1 << 2, "use AVX instructions"),
    PLL_ATTRIB_ARCH_AVX2(1 << 1, "use AVX2 instructions"),
    PLL_ATTRIB_ARCH_AVX512(1 << 3, "use AVX512 instructions"),
    
    PLL_SCALE_BUFFER_NONE(-1, "don't use scaling"),
    
    PLL_ATTRIB_PATTERN_TIP(1 << 4, "use states instead of partials at tips");
    ;

    PLLFlag(int mask, String meaning) {
        this.mask = mask;
        this.meaning = meaning;
    }

    public int getMask() {
        return mask;
    }

    public String getMeaning() {
        return meaning;
    }

    public boolean isSet(long flags) {
        return (flags & mask) != 0;
    }

    public static String toString(long flags) {
        StringBuilder sb = new StringBuilder();
        for (PLLFlag flag : PLLFlag.values()) {
            if (flag.isSet(flags)) {
                sb.append(" ").append(flag.name());
            }
        }
        return sb.toString();
    }

    private final int mask;
    private final String meaning;
}