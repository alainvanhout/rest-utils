package alainvanhout.endpoint.utils;

import alainvanhout.http.dtos.Response;
import org.junit.Assert;

import java.util.function.Consumer;

import static org.junit.Assert.*;

public class ResponseAssertionUtility {

    public static Consumer<Response> matches(int statusCode) {
        return response -> assertEquals(statusCode, response.getStatusCode());
    }
}
