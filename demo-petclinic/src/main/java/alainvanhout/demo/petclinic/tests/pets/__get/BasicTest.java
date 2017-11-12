package alainvanhout.demo.petclinic.tests.pets.__get;

import alainvanhout.demo.petclinic.api.PetClinicApiTest;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BasicTest extends PetClinicApiTest {

    @Test
    public void getAll() {
        api.pets
                .onError(response -> fail())
                .onSuccess(response -> assertEquals(200, response.getStatusCode()))
                .get();
    }
}
