package alainvanhout.endpoint.api;

import alainvanhout.http.client.HttpExecutor;

/**
 * Wrapper for all information that is relevant across the API, rather than a specific endpoint.
 */
public class Settings {

    /**
     * The {@link HttpExecutor} to be used to perform http calls.
     */
    private HttpExecutor httpExecutor;

    public HttpExecutor getHttpExecutor() {
        return httpExecutor;
    }

    public Settings httpExecutor(final HttpExecutor httpExecutor) {
        this.httpExecutor = httpExecutor;
        return this;
    }
}
