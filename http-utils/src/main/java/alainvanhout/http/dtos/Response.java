package alainvanhout.http.dtos;

import alainvanhout.json.JsonDefaults;
import alainvanhout.http.common.StatusCodeRange;
import alainvanhout.http.parameters.Parameters;
import alainvanhout.json.JsonConverter;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
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
    /**
     * Wrapper for the http header information to be used.
     */
    private Parameters headers = new Parameters();
    /**
     * Whether the http call timed out
     */
    private boolean timedOut;
    /**
     * The {@link JsonConverter} to be used. It needs not be set if no JSON-related functionality is used.
     */
    private JsonConverter jsonConverter;

    public int getStatusCode() {
        return statusCode;
    }

    public Response statusCode(final int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    /**
     * Convenience method for checking whether the response's http status code is within the given range.
     *
     * @param statusCodeRange The range to be checked against
     * @return Whether it is in the given range
     */
    public boolean inRange(StatusCodeRange statusCodeRange) {
        return statusCodeRange.matches(this.statusCode);
    }

    public String getBody() {
        return body;
    }

    /**
     * A convenience method to retrieve the information in the JSON-based response body, based on a {@link Class}
     * reference. This requires that a {@link JsonConverter} has been assigned to this request.
     *
     * @param clazz The class of object that is to be produced
     * @param <T>   The generic associated with the class of the object that is to be produced
     * @return The object that is to be produced
     */
    public <T> T getBodyFromJson(Class<T> clazz) {
        return jsonConverter.toObject(body, clazz);
    }

    /**
     * A convenience method to retrieve the information in the JSON-based response body, based on a {@link Type}
     * reference. This requires that a {@link JsonConverter} has been assigned to this request.
     *
     * @param type The Generic {@link Type} of the list that is to be produced
     * @param <T>  The generic associated with the type of the list that is to be produced
     * @return The list that is to be produced
     */
    public <T> List<T> getBodyFromJson(Type type) {
        final JsonConverter converter = actualJsonConverter();
        return converter.toList(body, type);
    }

    /**
     * Convenience method to retrieve the information in the JSON-based response body as {@link Map}
     * @return The Map that is produced
     */
    public Map<String, Object> getBodyAsMap() {
        final JsonConverter converter = actualJsonConverter();
        return converter.toMap(body);
    }

    private JsonConverter actualJsonConverter() {
        if (Objects.nonNull(jsonConverter)) {
            return jsonConverter;
        }
        final JsonConverter defaultJsonConverter = JsonDefaults.getDefaultJsonConverter();
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

    public Response duration(long duration) {
        this.duration = duration;
        return this;
    }

    public Parameters getHeaders() {
        return headers;
    }

    public Response headers(final Parameters headers) {
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

    public boolean isTimedOut() {
        return this.timedOut;
    }

    public Response timedOut(final boolean timedOut) {
        this.timedOut = timedOut;
        return this;
    }

    /**
     * Returns a string of the form '201 (36 ms)' in the case of an http call that returned with a 201 status code
     * within 36 milliseconds, for e.g. convenient logging.
     *
     * @return A string representation of the response
     */
    @Override
    public String toString() {
        if (timedOut) {
            return String.format("Timed out [%s ms]", duration);
        }
        return String.format("%s [%s ms]", statusCode, duration);
    }
}
