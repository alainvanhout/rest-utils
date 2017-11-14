package alainvanhout.endpoint.api;

/**
 * An exception subclass that is used in situations where expected issues can occur within endpoint-utils or its
 * implementations. This exception class should only be used when there are no java.lang exception classes that
 * are more appropriate.
 */
public class EndpointException extends RuntimeException {

    public EndpointException(String message) {
        super(message);
    }

    public EndpointException(String message, Throwable cause) {
        super(message, cause);
    }
}
