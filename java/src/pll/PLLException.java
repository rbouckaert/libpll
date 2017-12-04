package pll;

/**
 * @author Andrew Rambaut
 * @author Marc Suchard
 */
public class PLLException extends RuntimeException {
    public PLLException(String functionName, int errCode) {
        this.functionName = functionName;
        this.errCode = errCode;        
    }

    @Override
    public String getMessage() {
        PLLErrorCode code = null;
        for (PLLErrorCode c : PLLErrorCode.values()) {
            if (c.getErrCode() == errCode) {
                code = c;
            }
        }
        if (code == null) {
            return "BEAGLE function, " + functionName + ", returned error code " + errCode + " (unrecognized error code)";
        }
        return "BEAGLE function, " + functionName + ", returned error code " + errCode + " (" + code.getMeaning() + ")";
    }

    public String getFunctionName() {
        return functionName;
    }

    public int getErrCode() {
        return errCode;
    }

    private final String functionName;
    private final int errCode;
}
