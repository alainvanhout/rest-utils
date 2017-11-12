package alainvanhout.demo.petclinic.api.pets;

import alainvanhout.endpoint.api.Endpoint;
import alainvanhout.http.common.HttpMethod;
import alainvanhout.http.dtos.Request;

import java.util.List;

public class PetEndpoint extends Endpoint<PetEndpoint, Pet, List<Pet>> {

    private Pet instanceType;
    private List<Pet> listType;

    public Pet get() {
        final Request request = createRequest()
                .method(HttpMethod.GET);
        return performInstanceCall(request);
    }
}
