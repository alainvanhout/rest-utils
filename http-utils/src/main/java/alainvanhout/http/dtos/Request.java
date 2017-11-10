package alainvanhout.http.dtos;

import alainvanhout.http.common.Headers;
import alainvanhout.http.common.HttpMethod;
import alainvanhout.http.parameters.Parameters;
import alainvanhout.http.utils.Base64Utility;
import alainvanhout.json.JsonConverter;

public class Request {
    private String url;
    private String method;
    private String body;
    private Parameters parameters = new Parameters();
    private Parameters headers = new Parameters();
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

    public Parameters getParameters() {
        return parameters;
    }

    public Request parameters(final Parameters parameters) {
        this.parameters = parameters;
        return this;
    }

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

    public Request addHeaders(final String key, final String... values) {
        this.headers.add(key, values);
        return this;
    }

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

    @Override
    public String toString() {
        return String.format("%s %s", method, url);
    }
}
