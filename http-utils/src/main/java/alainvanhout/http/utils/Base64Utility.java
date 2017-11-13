package alainvanhout.http.utils;

import java.util.Base64;

/**
 * Utility for performing base-64 operations.
 */
public class Base64Utility {
    /**
     * Converts a string to its base-64 equivalent.
     *
     * @param input A regular string
     * @return The base-64 equivalent string
     */
    public static String toBase64String(final String input) {
        return new String(Base64.getEncoder().encode(input.getBytes()));
    }
}
