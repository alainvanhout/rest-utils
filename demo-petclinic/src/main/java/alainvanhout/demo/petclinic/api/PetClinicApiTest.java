package alainvanhout.demo.petclinic.api;

/**
 * Superclass to allow for convenient access to a configured and functional {@link PetClinicApi} instance.
 */
public class PetClinicApiTest  {

    protected PetClinicApi api = new PetClinicApi("http://localhost:9966/petclinic/api");
}
