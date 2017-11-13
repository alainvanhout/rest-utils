package alainvanhout.http;

/**
 * An exception subclass that is used in situations where expected issues can occur within http-utils or its
 * implementations. This exception class should only be used when there are no java.lang exception classes that
 * are more appropriate.
 */
public class HttpException extends RuntimeException {
    public HttpException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
