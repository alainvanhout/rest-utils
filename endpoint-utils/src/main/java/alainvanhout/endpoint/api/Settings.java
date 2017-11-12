package alainvanhout.endpoint.api;

import alainvanhout.http.client.HttpExecutor;

public class Settings {

    private HttpExecutor httpExecutor;

    public HttpExecutor getHttpExecutor() {
        return httpExecutor;
    }

    public Settings httpExecutor(final HttpExecutor httpExecutor) {
        this.httpExecutor = httpExecutor;
        return this;
    }
}
