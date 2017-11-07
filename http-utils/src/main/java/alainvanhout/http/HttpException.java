package alainvanhout.http;

public class HttpException extends RuntimeException {
    public HttpException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
