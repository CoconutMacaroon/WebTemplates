package org.blastoffnetwork;

public class ReplacementToken {
    int startLocation;
    int endLocation;

    public ReplacementToken(int startLocation, int endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    /**
     * Gets the raw token, exactly as parsed, with curly braces and original whitespace
     *
     * @param baseString The original string that we assume the token is part of. We won't store it in this class to minimize memory usage.
     * @return The raw token
     */
    public String getRawToken(String baseString) {
        // add one because the second argument is exclusive
        return baseString.substring(this.startLocation, this.endLocation + 1);
    }

    /**
     * Gets the token, with curly braces removed, and trailing and leading whitespace [from within the braces] also removed
     *
     * @param baseString The original string that we assume the token is part of. We won't store it in this class to minimize memory usage.
     * @return The processed token
     */
    public String getToken(String baseString) {
        // add one because the second argument is exclusive
        // and trim off the brackets, too
        final byte charactersToTrim = 2;
        return baseString
            .substring(
                this.startLocation + charactersToTrim,
                this.endLocation + 1 - charactersToTrim
            )
            .trim();  // and remove the trailing and leading whitespace from within the brackets
    }
}
