package org.blastoffnetwork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TemplateEvaluator {
    private TemplateEvaluator() {
        throw new IllegalStateException("Don't create an instance of a utility class");
    }

    /**
     * Takes an input file optionally containing expansion tokens (defined by
     * two open curly braces, a filename, followed by two closing curly braces),
     * and replaces all expansion tokens in the input with the contents of the
     * file specified within the expansion token. It will expand tokens within
     * files included by other expansion tokens. Infinite token loops (where two
     * or more files include each other) is undefined behaviour.
     *
     * @param inputData A {@code String} to use as input
     * @return The input {@code String}, but with all web templates evaluated
     * @throws IOException If there is an error when reading the file (i.e., if it doesn't exist)
     */
    public static String evaluateWebTemplates(String inputData, String sourceFileName) throws IOException, InvalidSyntaxException {
        // validate the input data
        {
            SyntaxValidator syntaxValidator = new SyntaxValidator(inputData, sourceFileName);
            if (!syntaxValidator.getValidity()) {
                //System.err.printf("%s:%d: %s", sourceFileName, syntaxValidator.getErrorLocation(), syntaxValidator.getErrorMessage());
                throw syntaxValidator.error;
            }
        }
        String workingData = inputData;

        ArrayList<ReplacementToken> tokens = Tokenizer.findTokens(workingData);
        // we count the number of tokens each time to support recursion
        // (having tokens inside files that were included via a token)
        SyntaxValidator tokenFileSyntaxValidator;
        while (Tokenizer.getNumberOfTokensInString(workingData) != 0) {
            // set the replacement token to the first one (we refresh the list
            // of tokens each iteration, so getting the first one works. It is
            // like DFS when nested tokens are used.
            ReplacementToken currentToken = tokens.get(0);
            String tokenFileContents = Files.readString(
                Paths.get(
                    currentToken.getToken(workingData)
                )
            );
            tokenFileSyntaxValidator = new SyntaxValidator(tokenFileContents, currentToken.getToken(inputData));
            if (!tokenFileSyntaxValidator.getValidity()) {
                //System.err.printf("%s:%d: %s", currentToken.getToken(workingData), tokenFileSyntaxValidator.getErrorLocation(), tokenFileSyntaxValidator.getErrorMessage());
                throw tokenFileSyntaxValidator.error;
            }
            workingData = StringUtils.removeCharacterRangeFromString(
                StringUtils.insertStringAtPosition(
                    workingData,
                    tokenFileContents,
                    currentToken.endLocation + 1
                ),
                currentToken.startLocation,
                currentToken.endLocation
            );

            // refresh token list to support nested tokens
            tokens = Tokenizer.findTokens(workingData);
        }
        return workingData;
    }
}
