package alainvanhout.endpoint.api;

import alainvanhout.http.dtos.Response;

import java.lang.reflect.Type;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * This class can be used as a superclass for concrete API client implementations.
 *
 * @param <T> A reference to the concrete class itself, to allow for chaining method call
 */
public abstract class BasicApi<T extends CallHandler> extends CallHandler<T> {

    /**
     * Setting up an API client requires a root url and a {@link Settings} instance
     *
     * @param rootUrl  The API root URL
     * @param settings The settings that are relevant across the API, rather than a specific endpoint.
     */
    public BasicApi(String rootUrl, Settings settings) {
        this.url = rootUrl;
        this.setSetting(settings);
        this.setOnSuccess(defaultOnSuccess());
        this.setOnError(defaultOnError());
    }

    /**
     * The action to be taken when a http call was made that resulted in a 200-series status code response.
     * This action is only used as a fallback, in case neither the given endpoint, nor any of its parents,
     * has had {@link CallHandler#onSuccess(BiConsumer)} set
     *
     * @return A consumer of {@link Response}
     */
    protected Consumer<Response> defaultOnSuccess() {
        return response -> {};
    }

    /**
     * The action to be taken when a http call was made that resulted in a non-200-series status code response.
     * This action is only used as a fallback, in case neither the given endpoint, nor any of its parents,
     * has had {@link CallHandler#onError(BiConsumer)} set
     *
     * @return A consumer of {@link Response}
     */
    protected Consumer<Response> defaultOnError() {
        return response -> {};
    }

    /**
     * The default type (e.g. class) to be used to deserialize the response body of a response with a
     * non-200-range http status code, if neither the endpoint in question nor any of its parents provides
     * an errorType to use.
     *
     * @return A type
     */
    protected Type defaultErrorType() {
        return null;
    }

    /**
     * Creating a new child instance is not needed for API clients
     *
     * @return the API client itself
     */
    @Override
    protected T createChildInstance() {
        return (T) this;
    }
}
