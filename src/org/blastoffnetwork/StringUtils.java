package org.blastoffnetwork;
public class StringUtils {
    private StringUtils() {
        throw new IllegalStateException("Don't create an instance of a utility class");
    }

    /**
     * Given a {@code String}, this method will return a new {@code String} without the characters in the specified range
     *
     * @param string The input {@code String}
     * @param start  The start of the range of characters to remove
     * @param end    The end of the range of characters to remove
     * @return The {@code String} with the characters in the specified range removed
     */
    public static String removeCharacterRangeFromString(String string, int start, int end) {
        return string.substring(0, start) + string.substring(end + 1);
    }

    /**
     * Inserts {@code addition} at index {@code index} in {@code baseString}
     *
     * @param baseString The base string to use
     * @param addition   The string to add
     * @param index   The index to insert {@code baseString} after
     * @return The string with {@code addition} following the character at {@code position} in {@code baseString}
     */
    public static String insertStringAtPosition(String baseString, String addition, int index) {
        return baseString.substring(0, index) + addition + baseString.substring(index);
    }
}
