package alainvanhout.demo.petclinic.tests.pets.__get;

import alainvanhout.http.apachecommons.HttpUtility;
import alainvanhout.http.common.HttpMethod;
import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A manual test that makes use of http-utils and json-utils, but not the API client based on endpoint-utils.
 * This involves slightly more boilerplate, but may allow for greater customization of the http call and the handling
 * of the response.
 */
public class ManualTest {

    @Test
    public void getPets_shouldBeSuccessful() {
        // build a http request dto
        final Request request = new Request()
                // set the specific url
                .url("http://localhost:9966/petclinic/api/pets")
                // set the http method/verb, making use of the convenient statically typed setter rather than the string setter
                .method(HttpMethod.GET);

        // perform the http call
        final Response response = HttpUtility.execute(request);

        // assert that the call was successfully performed
        assertEquals(200, response.getStatusCode());
    }
}
