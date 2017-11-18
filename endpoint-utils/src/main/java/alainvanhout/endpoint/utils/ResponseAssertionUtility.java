package alainvanhout.endpoint.utils;

import alainvanhout.http.common.StatusCodeRange;
import alainvanhout.http.dtos.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;

import static org.junit.Assert.*;

/**
 * Provides utility methods that supply a {@link Consumer<Response>} to perform common asserts.
 */
public class ResponseAssertionUtility {

    public static final String INCORRECT_STATUS_CODE = "Status code did not match expected value";

    /**
     * Asserts that the http status code in the {@link Response} must match the provided status code.
     * @param statusCode The status code
     * @return A consumer that performs the assert
     */
    public static Consumer<Response> matches(final int statusCode) {
        return response -> assertEquals(INCORRECT_STATUS_CODE, statusCode, response.getStatusCode());
    }

    /**
     * Asserts that the http status code in the {@link Response} must match the provided status code
     * and that the response body must be empty.
     * @param statusCode The status code
     * @return A consumer that performs the assert
     */
    public static Consumer<Response> matchesEmpty(final int statusCode) {
        return response -> {
            assertEquals(INCORRECT_STATUS_CODE, statusCode, response.getStatusCode());
            assertTrue("Response body was not empty", StringUtils.isEmpty(response.getBody()));
        };
    }

    /**
     * Asserts that the http status code in the {@link Response} must match the provided status code range.
     * @param statusCodeRange The status code range
     * @return A consumer that performs the assert
     */
    public static Consumer<Response> inRange(final StatusCodeRange statusCodeRange) {
        return response -> assertTrue(response.inRange(statusCodeRange));
    }

    /**
     * Asserts that the http status code in the {@link Response} must not be in the 200 range.
     * @return A consumer that performs the assert
     */
    public static Consumer<Response> unwantedSuccess() {
        return response -> fail("Unwanted success: " + response);
    }
}
