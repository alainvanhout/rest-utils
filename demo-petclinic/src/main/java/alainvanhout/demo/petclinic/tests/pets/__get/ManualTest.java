package alainvanhout.demo.petclinic.tests.pets.__get;

import alainvanhout.http.apachecommons.HttpUtility;
import alainvanhout.http.common.HttpMethod;
import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ManualTest {

    @Test
    public void getPets_shouldBeSuccessful() {
        final Request request = new Request()
                .url("http://localhost:9966/petclinic/api/pets")
                .method(HttpMethod.GET);

        final Response response = HttpUtility.execute(request);

        assertEquals(200, response.getStatusCode());
    }
}
