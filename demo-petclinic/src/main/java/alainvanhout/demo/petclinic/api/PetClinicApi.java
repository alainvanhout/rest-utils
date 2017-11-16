package alainvanhout.demo.petclinic.api;

import alainvanhout.demo.petclinic.api.pets.PetsEndpoint;
import alainvanhout.endpoint.api.BasicApi;
import alainvanhout.endpoint.api.CallHandler;
import alainvanhout.endpoint.api.Settings;
import alainvanhout.http.HttpDefaults;
import alainvanhout.http.apachecommons.HttpUtility;
import alainvanhout.http.dtos.Response;
import alainvanhout.json.gson.JsonUtility;

import java.util.function.Consumer;

/**
 * An example implementation of an API client, building endpoint-utils module.
 */
public class PetClinicApi extends BasicApi<PetClinicApi> {

    /**
     * To avoid each and every {@link alainvanhout.endpoint.api.Endpoint} implementation requiring an identical
     * constructor, the {@link alainvanhout.endpoint.api.Endpoint#init(String, CallHandler)} serves the same purpose.
     */
    public PetsEndpoint pets = new PetsEndpoint().init("pets", this);

    /**
     * An API client requires a root URL.
     *
     * @param rootUrl The root URL, to be passed on to {@link BasicApi}
     */
    public PetClinicApi(final String rootUrl) {
        super(rootUrl, new Settings().httpExecutor(HttpUtility.DEFAULT));
        // this ensures that a default JsonConverter is known globally, so it need not be set on every Request or Response
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
