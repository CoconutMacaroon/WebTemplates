package org.blastoffnetwork;

public class InvalidSyntaxException extends Exception {
    public static final byte NO_ERROR_LOCATION = -1;
    private final String errorFilename;
    private final int errorLocation;
    private final String errorMessage;


    public InvalidSyntaxException(int errorLocation, String errorMessage, String errorFilename) {
        super();
        this.errorLocation = errorLocation;
        this.errorMessage = errorMessage;
        this.errorFilename = errorFilename;
    }

    public int getErrorLocation() {
        return this.errorLocation;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
    public String getErrorFilename() {
        return this.errorFilename;
    }
}
