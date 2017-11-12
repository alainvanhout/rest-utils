package alainvanhout.endpoint.api;

public class EndpointException extends RuntimeException {

    public EndpointException(String message) {
        super(message);
    }

    public EndpointException(String message, Throwable cause) {
        super(message, cause);
    }
}