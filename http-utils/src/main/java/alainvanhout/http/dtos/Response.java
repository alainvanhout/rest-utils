package alainvanhout.http.dtos;

/**
 * Class that wraps the information of a http response
 */
public class Response {
    /**
     * The response status code, once received. Initial value is -1.
     */
    private int statusCode = -1;

    public int getStatusCode() {
        return statusCode;
    }

    public Response statusCode(final int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s", statusCode);
    }
}
