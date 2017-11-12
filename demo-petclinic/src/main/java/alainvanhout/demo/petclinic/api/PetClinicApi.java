package alainvanhout.demo.petclinic.api;

import alainvanhout.demo.petclinic.api.pets.PetsEndpoint;
import alainvanhout.endpoint.api.BasicApi;
import alainvanhout.endpoint.api.Settings;
import alainvanhout.http.HttpDefaults;
import alainvanhout.http.apachecommons.HttpUtility;
import alainvanhout.http.dtos.Response;
import alainvanhout.json.gson.JsonUtility;

import java.util.function.Consumer;

public class PetClinicApi extends BasicApi<PetClinicApi> {

    public PetsEndpoint pets = new PetsEndpoint().init("pets", this);

    public PetClinicApi(final String url) {
        super(url, new Settings().httpExecutor(HttpUtility.DEFAULT));
        HttpDefaults.setDefaultJsonConverter(JsonUtility.DEFAULT);
    }

    @Override
    protected Consumer<Response> defaultOnSuccess() {
        return response -> {};
    }

    @Override
    protected Consumer<Response> defaultOnError() {
        return response -> {};
    }
}
