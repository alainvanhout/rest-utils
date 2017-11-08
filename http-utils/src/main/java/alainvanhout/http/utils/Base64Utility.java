package alainvanhout.http.utils;

import java.util.Base64;

public class Base64Utility {
    public static String toBase64String(final String input){
        return new String(Base64.getEncoder().encode(input.getBytes()));
    }
}
