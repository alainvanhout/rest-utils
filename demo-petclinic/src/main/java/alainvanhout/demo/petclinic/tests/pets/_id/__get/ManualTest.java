package alainvanhout.demo.petclinic.tests.pets._id.__get;

import alainvanhout.demo.petclinic.api.pets.Pet;
import alainvanhout.http.apachecommons.HttpUtility;
import alainvanhout.http.common.HttpMethod;
import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;
import alainvanhout.json.gson.JsonUtility;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ManualTest {

    private static final String INT_MISMATCH_EXCEPTION = "org.springframework.web.method.annotation.MethodArgumentTypeMismatchException";
    private static final String INT_MISMATCH_MESSAGE = "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"non_int_id\"";

    @Test
    public void getPets_whenPetExists_shouldBeSuccessful() {
        final Request request = new Request()
                .url("http://localhost:9966/petclinic/api/pets/1")
                .jsonConverter(JsonUtility.DEFAULT)
                .method(HttpMethod.GET);

        final Response response = HttpUtility.execute(request);
        final Pet pet = response.getBodyFromJson(Pet.class);

        assertEquals(200, response.getStatusCode());
        assertEquals(pet.getId(), "1");
        assertEquals(pet.getName(), "Leo");
        assertEquals(pet.getBirthDate(), "2010/09/07");
    }

    @Test
    public void getPets_whenPetDoesNotExist_shouldReturnNotFound() {
        final Request request = new Request()
                .url("http://localhost:9966/petclinic/api/pets/9999")
                .jsonConverter(JsonUtility.DEFAULT)
                .method(HttpMethod.GET);

        final Response response = HttpUtility.execute(request);

        assertEquals(404, response.getStatusCode());
    }

    @Test
    public void getPets_whenIdNotAnInteger_shouldReturnValidationError() {
        final Request request = new Request()
                .url("http://localhost:9966/petclinic/api/pets/non_int_id")
                .jsonConverter(JsonUtility.DEFAULT)
                .method(HttpMethod.GET);

        final Response response = HttpUtility.execute(request);
        final Map<String, String> error = response.getBodyFromJson(HashMap.class);

        assertEquals(400, response.getStatusCode());
        assertEquals(INT_MISMATCH_EXCEPTION, error.get("className"));
        assertEquals(INT_MISMATCH_MESSAGE, error.get("exMessage"));
    }
}
