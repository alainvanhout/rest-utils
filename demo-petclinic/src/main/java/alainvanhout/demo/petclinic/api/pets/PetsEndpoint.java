package alainvanhout.demo.petclinic.api.pets;

import alainvanhout.endpoint.api.Endpoint;
import alainvanhout.http.common.HttpMethod;
import alainvanhout.http.dtos.Request;

import java.util.List;

public class PetsEndpoint extends Endpoint<PetsEndpoint, Pet, List<Pet>> {

    private Pet instanceType;
    private List<Pet> listType;

    public List<Pet> get() {
        final Request request = createRequest()
                .method(HttpMethod.GET);
        return performListCall(request);
    }

    public PetEndpoint id(final String id) {
        return new PetEndpoint().init(url + "/" + id, settings);
    }
}
