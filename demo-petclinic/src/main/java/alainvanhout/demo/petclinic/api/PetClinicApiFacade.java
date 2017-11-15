package alainvanhout.demo.petclinic.api;

import alainvanhout.demo.petclinic.api.pets.Pet;

import java.util.List;

/**
 * An API client facade which assumes 'happy flows' and will fail-fast in case of errors.
 */
public class PetClinicApiFacade {

    private final PetClinicApi api;

    /**
     * builds on an API client
     * @param rootUrl The API root url
     */
    public PetClinicApiFacade(final String rootUrl) {
        api = new PetClinicApi(rootUrl)
                // should fail-fast
                .onErrorThrow();
    }

    public List<Pet> getPets() {
        return api.pets
                .get();
    }

    public Pet getPet(final String id) {
        return api.pets.id(id)
                .get();
    }
}
