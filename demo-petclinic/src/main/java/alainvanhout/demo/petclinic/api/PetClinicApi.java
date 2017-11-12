package alainvanhout.demo.petclinic.api;

import alainvanhout.demo.petclinic.api.pets.PetsEndpoint;
import alainvanhout.endpoint.api.BasicApi;
import alainvanhout.endpoint.api.Settings;
import alainvanhout.http.HttpDefaults;
import alainvanhout.http.apachecommons.HttpUtility;
import alainvanhout.json.gson.JsonUtility;

public class PetClinicApi extends BasicApi {

    public PetsEndpoint pets;

    public PetClinicApi(final String url) {
        super(new Settings()
                .httpExecutor(HttpUtility.DEFAULT));

        HttpDefaults.setDefaultJsonConverter(JsonUtility.DEFAULT);

        pets = new PetsEndpoint().init(url + "pets", settings);
    }
}
