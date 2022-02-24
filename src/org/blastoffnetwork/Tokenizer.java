package org.blastoffnetwork;

import java.util.ArrayList;

public class Tokenizer {
    /**
     * Determines how many tokens there are in a given String. The given String
     * is assumed to have valid syntax
     *
     * @param string The String to count tokens in
     * @return The number of tokens in {@code string}
     */
    public static int getNumberOfTokensInString(String string) {
        return findTokens(string).size();
    }

    /**
     * Finds all the tokens (strings within double curly braces), and returns them
     *
     * @param input The input string to parse. Assumed to be valid syntax.
     * @return An ArrayList of all the tokens in the input string
     */
    public static ArrayList<ReplacementToken> findTokens(String input) {
        // we will store the tokens in an ArrayList,
        // as that makes it easy to add more tokens
        ArrayList<ReplacementToken> tokens = new ArrayList<>();

        // iterate over every character in the input
        for (int i = 0; i < input.length(); i++) {
            // if the start of a token is detected
            if (input.charAt(i) == '{' && input.charAt(i + 1) == '{') {
                // note the token start location
                int tokenStart = i;

                // find the end of the token
                for (int j = i; j < input.length(); j++) {
                    if (input.charAt(j - 1) == '}' && input.charAt(j) == '}') {
                        // note where the token ends
                        int tokenEnd = j;

                        // set i to j, as we've already processed that token
                        i = j;

                        // and add the token to the list
                        tokens.add(new ReplacementToken(tokenStart, tokenEnd));

                        // stop searching for the end of the token now that we've found it
                        break;
                    }
                }
            }
        }
        return tokens;
    }
}
