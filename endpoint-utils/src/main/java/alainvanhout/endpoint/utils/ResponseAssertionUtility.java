package alainvanhout.endpoint.utils;

import alainvanhout.http.dtos.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;

import static org.junit.Assert.*;

public class ResponseAssertionUtility {

    public static final String INCORRECT_STATUS_CODE = "Status code did not match expected value";

    public static Consumer<Response> matches(int statusCode) {
        return response -> assertEquals(INCORRECT_STATUS_CODE, statusCode, response.getStatusCode());
    }

    public static Consumer<Response> matchesEmpty(int statusCode) {
        return response -> {
            assertEquals(INCORRECT_STATUS_CODE, statusCode, response.getStatusCode());
            assertTrue("Response body was not empty", StringUtils.isEmpty(response.getBody()));
        };
    }

    public static Consumer<Response> unwantedSuccess() {
        return response -> fail("Unwanted success: " + response);
    }
}
