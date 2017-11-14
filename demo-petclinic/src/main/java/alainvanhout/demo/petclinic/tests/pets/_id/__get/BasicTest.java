package alainvanhout.demo.petclinic.tests.pets._id.__get;

import alainvanhout.demo.petclinic.api.PetClinicApiTest;
import alainvanhout.demo.petclinic.api.pets.Pet;
import alainvanhout.endpoint.utils.ResponseAssertionUtility;
import alainvanhout.http.dtos.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static alainvanhout.endpoint.utils.ResponseAssertionUtility.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A test of the GET /pets/{id} call that makes use of the PetClinicApi to reduce boilerplate.
 */
public class BasicTest extends PetClinicApiTest {

    private static final String INT_MISMATCH_EXCEPTION = "org.springframework.web.method.annotation.MethodArgumentTypeMismatchException";
    private static final String INT_MISMATCH_MESSAGE = "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"non_int_id\"";

    /**
     * Happy path test for an existing demo-data pet.
     */
    @Test
    public void getPets_whenPetExists_shouldBeSuccessful() {
        final Pet pet = api.pets.id("1")
                .onErrorThrow()
                .onSuccess(matches(200))
                .get();

        assertEquals(pet.getId(), "1");
        assertEquals(pet.getName(), "Leo");
        assertEquals(pet.getBirthDate(), "2010/09/07");
    }

    /**
     * Sad flow: pet not found.
     */
    @Test
    public void getPets_whenPetDoesNotExist_shouldReturnNotFound() {
        api.pets.id("9999")
                .onError(matchesEmpty(404))
                .onSuccess(unwantedSuccess())
                .get();
    }

    /**
     * Sad flow: {id} is not an integer.
     * This includes manual checking of the error code and message inside the response body.
     */
    @Test
    public void getPets_whenIdNotAnInteger_shouldReturnValidationError() {
        api.pets.id("non_int_id")
                .onError(response -> {
                    final Map<String, String> error = response.getBodyFromJson(HashMap.class);
                    assertEquals(400, response.getStatusCode());
                    assertEquals(INT_MISMATCH_EXCEPTION, error.get("className"));
                    assertEquals(INT_MISMATCH_MESSAGE, error.get("exMessage"));
                })
                .onSuccess(unwantedSuccess())
                .get();
    }
}
