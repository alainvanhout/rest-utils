package alainvanhout.http.dtos;

import alainvanhout.http.common.StatusCodeRange;

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

    @Override
    public String toString() {
        return String.format("%s [%s ms]", statusCode, duration);
    }
}
