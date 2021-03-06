package alainvanhout.http.apachecommons;

import alainvanhout.http.apachecommons.client.HttpExecutorImpl;
import alainvanhout.http.client.HttpExecutor;
import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;
import alainvanhout.json.gson.JsonUtility;

/**
 * Provides specific convenience builds of {@link HttpExecutor}.
 */
public class HttpUtility {

    /**
     * A {@link HttpExecutor} instance that relies for all configuration relies on the defaults.
     */
    public static final HttpExecutor DEFAULT = HttpExecutorImpl.with().jsonConverter(JsonUtility.DEFAULT).init();

    public static Response execute(final Request request) {
        return DEFAULT.execute(request);
    }
}
