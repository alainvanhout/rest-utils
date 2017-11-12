package alainvanhout.demo.petclinic.tests.pets._id.__get;

import alainvanhout.demo.petclinic.api.PetClinicApiTest;
import alainvanhout.demo.petclinic.api.pets.Pet;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BasicTest extends PetClinicApiTest {

    private static final String INT_MISMATCH_EXCEPTION = "org.springframework.web.method.annotation.MethodArgumentTypeMismatchException";
    private static final String INT_MISMATCH_MESSAGE = "Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"non_int_id\"";

    @Test
    public void getPets_whenPetExists_shouldBeSuccessful() {
        final Pet pet = api.pets.id("1")
                .onError(response -> fail())
                .get();

        assertEquals(pet.getId(), "1");
        assertEquals(pet.getName(), "Leo");
        assertEquals(pet.getBirthDate(), "2010/09/07");
    }

    @Test
    public void getPets_whenPetDoesNotExist_shouldReturnNotFound() {
        api.pets.id("9999")
                .onError(response -> assertEquals(404, response.getStatusCode()))
                .onSuccess(response -> fail())
                .get();
    }

    @Test
    public void getPets_whenIdNotAnInteger_shouldReturnValidationError() {
        api.pets.id("non_int_id")
                .onError(response -> {
                    final Map<String, String> error = response.getBodyFromJson(HashMap.class);
                    assertEquals(400, response.getStatusCode());
                    assertEquals(INT_MISMATCH_EXCEPTION, error.get("className"));
                    assertEquals(INT_MISMATCH_MESSAGE, error.get("exMessage"));
                })
                .onSuccess(response -> fail())
                .get();
    }
}
