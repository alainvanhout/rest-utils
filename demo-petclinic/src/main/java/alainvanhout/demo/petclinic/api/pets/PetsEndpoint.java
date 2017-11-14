package alainvanhout.demo.petclinic.api.pets;

import alainvanhout.endpoint.api.Endpoint;
import alainvanhout.http.common.HttpMethod;
import alainvanhout.http.dtos.Request;

import java.util.List;

/**
 * The /pets endpoint
 */
public class PetsEndpoint extends Endpoint<PetsEndpoint, Pet, List<Pet>> {

    /**
     * Via naming convention and reflection, these fields allow for out-of-the-box JSON conversion where needed.
     */
    private Pet instanceType;
    private List<Pet> listType;

    /**
     * Method to perform a GET /pets call
     *
     * @return The list of Pet that was returned via the http response, if any
     */
    public List<Pet> get() {
        // the createRequest method supplied a Request that already has some minimal information, such as the url
        final Request request = createRequest()
                .method(HttpMethod.GET);
        // performListCall will perform the http call and expect an list of Pet to be returned when successful
        return performListCall(request);
    }

    /**
     * Method to go to an endpoint for /pets/{id}, as encapsulated by {@link PetEndpoint}
     * @param id The id of the Pet
     * @return A {@link PetEndpoint} instance for that specific (pet) id
     */
    public PetEndpoint id(final String id) {
        return new PetEndpoint().init(id, this);
    }
}
