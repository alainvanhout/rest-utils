package alainvanhout.endpoint.api;

import alainvanhout.http.client.HttpExecutor;
import alainvanhout.json.JsonConverter;

/**
 * Wrapper for all information that is relevant across the API, rather than a specific endpoint.
 */
public class Settings {

    /**
     * The {@link HttpExecutor} to be used to perform http calls.
     */
    private HttpExecutor httpExecutor;
    /**
     * The {@link JsonConverter} to be used to convert to and from JSON. If not provided, the default JsonConverter
     * will be used, if set.
     */
    private JsonConverter jsonConverter;
    /**
     * Username used for basic authentication.
     */
    private String username;
    /**
     * Password used for basic authentication.
     */
    private String password;

    public HttpExecutor getHttpExecutor() {
        return httpExecutor;
    }

    public Settings httpExecutor(final HttpExecutor httpExecutor) {
        this.httpExecutor = httpExecutor;
        return this;
    }

    public Settings username(final String username) {
        this.username = username;
        return this;
    }

    public Settings password(final String password) {
        this.password = password;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Settings jsonConverter(final JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
        return this;
    }

    public JsonConverter getJsonConverter() {
        return jsonConverter;
    }
}
