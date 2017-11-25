package alainvanhout.demo.petclinic.api.pets;

import alainvanhout.endpoint.api.Endpoint;
import alainvanhout.http.common.HttpMethod;
import alainvanhout.http.dtos.Request;

/**
 * The /pets/{id} endpoint
 */
public class PetEndpoint extends Endpoint<PetEndpoint, Pet> {

    /**
     * Via naming convention and reflection, these fields allow for out-of-the-box JSON conversion where needed.
     */
    private Pet instanceType;

    /**
     * Method to perform a GET /pets/{id} call
     *
     * @return The Pet that was returned via the http response, if any
     */
    public Pet get() {
        // the createRequest method supplied a Request that already has some minimal information, such as the url
        final Request request = createRequest()
                .method(HttpMethod.GET);
        // performInstanceCall will perform the http call and expect an instance of Pet to be returned when successful
        return performInstanceCall(request);
    }
}
