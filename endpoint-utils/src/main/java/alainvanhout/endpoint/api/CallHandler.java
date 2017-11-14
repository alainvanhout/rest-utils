package alainvanhout.endpoint.api;

import alainvanhout.http.dtos.Response;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * A superclass that wraps that encapsulates information and behaviour relates to setting and http call response handling.
 *
 * @param <T> A reference to the concrete class itself, to allow for chaining method call
 */
public class CallHandler<T extends CallHandler> {

    protected String url;
    private Settings settings;

    private CallHandler parent;

    private Consumer<Response> onError;
    private Consumer<Response> onSuccess;

    public T onError(Consumer<Response> consumer) {
        return (T) createChildInstance().setOnError(consumer);
    }

    public T onErrorThrow() {
        return onError(response -> {
            throw new EndpointException(String.format("Encountered error response: %s", response));
        });
    }

    public T onSuccess(Consumer<Response> consumer) {
        return (T) createChildInstance().setOnSuccess(consumer);
    }

    T setOnError(Consumer<Response> consumer) {
        this.onError = consumer;
        return (T) this;
    }

    T setOnSuccess(Consumer<Response> consumer) {
        this.onSuccess = consumer;
        return (T) this;
    }

    private T createChildInstance() {
        try {
            return (T) this.getClass().newInstance()
                    .parent(this);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EndpointException("Failed to create new instance of " + getClass().getCanonicalName(), e);
        }
    }

    Settings getSettings() {
        if (Objects.nonNull(settings)) {
            return settings;
        }
        if (Objects.nonNull(parent)) {
            return parent.getSettings();
        }
        return null;
    }

    Consumer<Response> getOnError() {
        if (Objects.nonNull(onError)) {
            return onError;
        }
        if (Objects.nonNull(parent)) {
            return parent.getOnError();
        }
        return null;
    }

    Consumer<Response> getOnSuccess() {
        if (Objects.nonNull(onSuccess)) {
            return onSuccess;
        }
        if (Objects.nonNull(parent)) {
            return parent.getOnSuccess();
        }
        return null;
    }


    public String getUrl() {
        if (Objects.isNull(url) && Objects.nonNull(parent)) {
            return parent.getUrl();
        }
        if (Objects.isNull(parent)) {
            return url;
        }
        return parent.getUrl() + "/" + url;
    }

    CallHandler url(final String url) {
        this.url = url;
        return this;
    }

    CallHandler parent(final CallHandler parent) {
        this.parent = parent;
        return this;
    }

    CallHandler setSetting(Settings settings) {
        this.settings = settings;
        return this;
    }
}
