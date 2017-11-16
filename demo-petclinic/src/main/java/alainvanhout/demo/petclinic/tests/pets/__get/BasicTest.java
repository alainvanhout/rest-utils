package alainvanhout.demo.petclinic.tests.pets.__get;

import alainvanhout.demo.petclinic.api.PetClinicApiTest;
import org.junit.Test;

import static alainvanhout.endpoint.utils.ResponseAssertionUtility.matches;

/**
 * A test of the GET /pets call that makes use of the PetClinicApi to reduce boilerplate.
 */
public class BasicTest extends PetClinicApiTest {

    /**
     * A simple test method that calls GET /pets and verifies that no non-200-status code was returned
     * and that the exact status code that was returned is 200.
     *
     * Note that onError/onErrorThrow and onSuccess are handled separately to allow for greater flexibility
     * and configurability.
     */
    @Test
    public void getAll() {
        // declare that you want to test against the /pets endpoint
        api.pets
                // declare that any non-200 response should throw an exception
                .onErrorThrow()
                // makes use of the convenience method 'matches' from ResponseAssertionUtility
                .onSuccess(matches(200))
                // perform the actual GET http call
                .get();
    }
}
