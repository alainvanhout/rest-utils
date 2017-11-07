package alainvanhout.http.dtos;

import alainvanhout.http.common.StatusCodeRange; /**
 * Class that wraps the information of a http response
 */
public class Response {
    /**
     * The response status code, once received. Initial value is -1.
     */
    private int statusCode = -1;
    private String body;

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public Response statusCode(final int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Response body(final String body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s", statusCode);
    }

    public boolean inRange(StatusCodeRange statusCodeRange) {
        return statusCodeRange.matches(this.statusCode);
    }
}
