package alainvanhout.http.dtos;

import alainvanhout.http.common.Headers;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void getParameters_shouldStartNonNull() {
        assertNotNull(new Request().getParameters());
    }

    @Test
    public void getHeaders_shouldStartNonNull() {
        assertNotNull(new Request().getHeaders());
    }

    @Test
    public void basicAuthentication_shouldAddCorrectlyEncodedAuthorizationHeader() {
        Request request = new Request()
                .basicAuthentication("hello", "world");

        String authorizationHeader = request.getHeaders().getOne(Headers.AUTHORIZATION);
        assertEquals("Basic aGVsbG86d29ybGQ=", authorizationHeader);
    }
}