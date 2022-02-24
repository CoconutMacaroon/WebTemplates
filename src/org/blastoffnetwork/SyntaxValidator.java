package org.blastoffnetwork;

public class SyntaxValidator {
    /**
     * {@code data} is the {@code String} that gets validated
     */
    private final String data;
    private final String filename;
    InvalidSyntaxException error;
    private boolean isValid = true;

    public SyntaxValidator(String data, String filename) {
        this.data = data;
        this.filename = filename;
        validate();
    }

    /**
     * Gets the validity of the input - note that validity is checked once
     * when the constructor is called, not each time this method is called
     *
     * @return If the input is valid
     */
    public boolean getValidity() {
        return this.isValid;
    }

    /**
     * If the input is not valid, this method will return the error
     *
     * @return The error message, if any
     * @throws UnsupportedOperationException if the input is valid, as there
     *                                       would be no error message to get
     */
    public InvalidSyntaxException getErrorMessage() throws UnsupportedOperationException {
        if (this.isValid) {
            throw new UnsupportedOperationException();
        }
        return this.error;
    }

    /**
     * Validates the input to a {@link SyntaxValidator} to see if it is valid.
     * This method does NOT validate the syntax of any files that get included.
     */
    private void validate() {
        // this variable is if the portion of the input we've already checked so
        // far is valid - by default, we haven't checked anything, and an empty
        // string is valid, so we set it to true by default
        boolean isInputValidSoFar = true;

        // iterate over every character in the input
        int tokenStart = -1;
        for (int i = 0; i < this.data.length(); i++) {
            // if the start of a token is detected
            if (this.data.charAt(i) == '{' && i != this.data.length() - 1 && this.data.charAt(i + 1) == '{') {
                // note the token start location
                tokenStart = i;
                isInputValidSoFar = false;
            }
            // find the end of the token
            if (this.data.charAt(i) == '}' && i != this.data.length() - 1 && this.data.charAt(i + 1) == '}') {
                tokenStart = -1;
                isInputValidSoFar = true;
            }
            // if we've reached the end of the input without finding two matching braces
            if (!isInputValidSoFar && i == this.data.length() - 1) {
                this.error = new InvalidSyntaxException(tokenStart, "End of input reached without matching closing brace", filename);
            }
        }
        // once we've checked the entire string, we know if it is valid
        this.isValid = isInputValidSoFar;
    }
}
