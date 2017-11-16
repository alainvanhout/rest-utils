package alainvanhout.demo.petclinic.api;

/**
 * Superclass to allow for convenient access to a configured and functional {@link PetClinicApi} instance.
 */
public class PetClinicApiTest  {

    public static final String ROOT_URL = "http://localhost:9966/petclinic/api";

    /**
     * A convenience API client
     */
    protected final PetClinicApi api = new PetClinicApi(ROOT_URL);

    /**
     * A convenience API client facade
     */
    protected final PetClinicApiFacade facade = new PetClinicApiFacade(ROOT_URL);
}
