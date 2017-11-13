package alainvanhout.http.dtos;

import alainvanhout.http.HttpDefaults;
import alainvanhout.http.common.Headers;
import alainvanhout.http.common.HttpMethod;
import alainvanhout.http.parameters.Parameters;
import alainvanhout.http.utils.Base64Utility;
import alainvanhout.json.JsonConverter;

import java.util.Objects;

import static alainvanhout.http.common.Headers.CONTENT_TYPE;
import static alainvanhout.http.common.MimeTypes.APPLICATION_JSON;

/**
 * A dto that wraps all information necessary to perform an http request. The information is all openly modifiable and
 * can easily be retrieved. Convenience methods are provided, but behaviour is purposely kept to a minimum.
 */
public class Request {
    /**
     * The Uniform Resource Locator (URL) that is to be used.
     */
    private String url;
    /**
     * The http method (also referred to as verb) to be used.
     */
    private String method;
    /**
     * The request body to be used. May be left blank.
     */
    private String body;
    /**
     * Wrapper for the query parameter information to be used.
     */
    private Parameters parameters = new Parameters();
    /**
     * Wrapper for the http header information to be used.
     */
    private Parameters headers = new Parameters();
    /**
     * The {@link JsonConverter} to be used. It needs not be set if no JSON-related functionality is used.
     */
    private JsonConverter jsonConverter;

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public Request url(final String url) {
        this.url = url;
        return this;
    }

    public Request method(final String method) {
        this.method = method;
        return this;
    }

    public Request method(final HttpMethod method) {
        this.method = method.name();
        return this;
    }

    public Request body(final String body) {
        this.body = body;
        return this;
    }

    /**
     * Convenience method to set the body to a JSON-representation of a given object
     *
     * @param body The object to be converted to JSON
     * @return The request itself
     */
    public Request bodyAsJson(final Object body) {
        final JsonConverter converter = actualJsonConverter();
        headers.add(CONTENT_TYPE, APPLICATION_JSON);
        return body(converter.toJson(body));
    }

    private JsonConverter actualJsonConverter() {
        if (Objects.nonNull(jsonConverter)) {
            return jsonConverter;
        }
        final JsonConverter defaultJsonConverter = HttpDefaults.getDefaultJsonConverter();
        if (Objects.nonNull(defaultJsonConverter)) {
            return defaultJsonConverter;
        }
        throw new IllegalStateException("No JsonConverter has been assigned");
    }

    public Parameters getParameters() {
        return parameters;
    }

    public Request parameters(final Parameters parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * Convenience method to add a query parameter, with one or more values.
     *
     * @param key    The query parameter key
     * @param values The query parameter values (may be zero, one or more)
     * @return The request itself
     */
    public Request addParameters(final String key, final String... values) {
        this.parameters.add(key, values);
        return this;
    }

    public Parameters getHeaders() {
        return headers;
    }

    public Request headers(final Parameters headers) {
        this.headers = headers;
        return this;
    }

    /**
     * Convenience method to add an http header, with one or more values.
     *
     * @param key    The query parameter key
     * @param values The query parameter values (may be zero, one or more)
     * @return The request itself
     */
    public Request addHeaders(final String key, final String... values) {
        this.headers.add(key, values);
        return this;
    }

    /**
     * A convenience method to add a basic authentication header to the request.
     *
     * @param username The username
     * @param password The password
     * @return The request itself
     */
    public Request basicAuthentication(String username, String password) {
        final String authentication = Base64Utility.toBase64String(username + ":" + password);
        return addHeaders(Headers.AUTHORIZATION, "Basic " + authentication);
    }

    public JsonConverter getJsonConverter() {
        return jsonConverter;
    }

    public Request jsonConverter(final JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
        return this;
    }

    /**
     * Returns a string of the form 'GET http://example.com', for e.g. convenient logging.
     *
     * @return A string representation of the request
     */
    @Override
    public String toString() {
        return String.format("%s %s", method, url);
    }
}
