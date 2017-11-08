package alainvanhout.http.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class Base64UtilityTest {
    @Test
    public void toBase64String() throws Exception {
        assertEquals("YWxhZGRpbjpvcGVuc2VzYW1l", Base64Utility.toBase64String("aladdin:opensesame"));
    }
}