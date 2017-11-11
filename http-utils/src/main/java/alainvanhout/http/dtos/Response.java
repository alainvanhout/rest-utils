package alainvanhout.http.dtos;

import alainvanhout.http.HttpDefaults;
import alainvanhout.http.common.StatusCodeRange;
import alainvanhout.http.parameters.Parameters;
import alainvanhout.json.JsonConverter;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

/**
 * Class that wraps the information of a http response
 */
public class Response {
    /**
     * The response status code, once received. Initial value is -1.
     */
    private int statusCode = -1;
    /**
     * The response body represented as a string
     */
    private String body;
    /**
     * The duration of the http call
     */
    private long duration;

    private JsonConverter jsonConverter;

    private Parameters headers = new Parameters();

    public int getStatusCode() {
        return statusCode;
    }

    public Response statusCode(final int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public boolean inRange(StatusCodeRange statusCodeRange) {
        return statusCodeRange.matches(this.statusCode);
    }

    public String getBody() {
        return body;
    }

    public <T> T getBodyFromJson(Class<T> clazz) {
        return jsonConverter.toObject(body, clazz);
    }

    public <T> List<T> getBodyFromJson(Type type) {
        JsonConverter converter = actualJsonConverter();
        return converter.toList(body, type);
    }

    private JsonConverter actualJsonConverter(){
        if (Objects.nonNull(jsonConverter)) {
            return jsonConverter;
        }
        final JsonConverter defaultJsonConverter = HttpDefaults.getDefaultJsonConverter();
        if (Objects.nonNull(defaultJsonConverter)) {
            return defaultJsonConverter;
        }
        throw new IllegalStateException("No JsonConverter has been assigned");
    }

    public Response body(final String body) {
        this.body = body;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Parameters getHeaders() {
        return headers;
    }

    public Response headers(final Parameters headers) {
        this.headers = headers;
        return this;
    }

    public Response addHeaders(final String key, final String... values) {
        this.headers.add(key, values);
        return this;
    }

    public JsonConverter getJsonConverter() {
        return jsonConverter;
    }

    public Response jsonConverter(final JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s [%s ms]", statusCode, duration);
    }

}
