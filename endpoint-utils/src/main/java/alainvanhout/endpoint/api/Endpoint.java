package alainvanhout.endpoint.api;

import alainvanhout.http.client.HttpExecutor;
import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;

import static alainvanhout.http.common.StatusCodeRange._200;

/**
 * This class can be used as a superclass for concrete endpoint implementations, which serve to facilitate calls to the
 * actual API's endpoints.
 * <p>
 * The class is built around the idea that a given endpoint returns a since resource, or lists of that same resource.
 * For convenience, it will look for a field of type {@link Class} called 'instanceType' and a field of type
 * {@link Type} called listType. Based on this simple convention, and some reflection, out-of-the-box JSON-conversion
 * is performed where needed. These fields need not be given an actual value
 * <p>
 * This could e.g. take the form:
 * <p>
 * private Pet instanceType;
 * private List&lt;Pet&gt; listType;
 *
 * @param <T> A reference to the concrete class itself, to allow for chaining method call
 * @param <U> A generics reference to the instance type, e.g. MyApiResponseDto
 * @param <V> A generics reference to the list type, e.g. List&lt;MyApiResponseDto&gt;
 */
public class Endpoint<T extends Endpoint, U, V> extends CallHandler<T> {

    /**
     * A concrete reference to the {@link Class} that serves as the dto for this resource.
     * It is extracted from the field of the same name that by convention is added to the
     * {@link Endpoint} implementations.
     */
    private Class<U> instanceType;
    /**
     * A concrete reference to a {@link Type} that serves as the dto for this resource.
     * It is extracted from the field of the same name that by convention is added to the
     * {@link Endpoint} implementations.
     */
    private Type listType;
    /**
     * A concrete reference to the {@link Class} or {@link Type} that serves as the error dto for this endpoint.
     * It is extracted from the field of the same name that by convention is added to the
     * {@link Endpoint} implementations.
     */
    private final Type errorType;

    /**
     * Creating an instance of an {@link Endpoint} class will automatically cause extraction of the instanceType
     * and listType information from the concrete class, as explained in other places of this class' Javadoc.
     */
    public Endpoint() {
        this.instanceType = extractInstanceType();
        this.listType = extractListType();
        this.errorType = extractErrorType();
    }

    public T init(final String url, final CallHandler parent) {
        this.instanceType = extractInstanceType();
        this.listType = extractListType();
        this.url = url;
        this.parent(parent);
        return (T) this;
    }

    private Class<U> extractInstanceType() {
        try {
            return (Class<U>) this.getClass().getDeclaredField("instanceType").getType();
        } catch (NoSuchFieldException e) {
            // disregard, since the instanceType might not be needed
            return null;
        }
    }

    private Type extractListType() {
        try {
            return this.getClass().getDeclaredField("listType").getGenericType();
        } catch (NoSuchFieldException e) {
            // disregard, since the errorType might not be needed
            return null;
        }
    }

    private Type extractErrorType() {
        try {
            return this.getClass().getDeclaredField("errorType").getGenericType();
        } catch (NoSuchFieldException e) {
            // disregard, since the listType might not be needed, or is to be retrieved from the parent
            return HashMap.class;
        }
    }

    /**
     * To be used as a basis for performing calls against this particular endpoint.
     *
     * @return A {@link Request} with minimal endpoint information already applied
     */
    protected Request createRequest() {
        return new Request()
                .url(getUrl());
    }

    protected U performInstanceCall(final Request request) {
        if (Objects.isNull(instanceType)) {
            throw new EndpointException("No field 'instanceType' found on class " + getClass().getCanonicalName());
        }

        final HttpExecutor httpExecutor = getSettings().getHttpExecutor();
        final Response response = httpExecutor.execute(request);
        return handleResponse(response, instanceType);
    }

    protected V performListCall(final Request request) {
        if (Objects.isNull(listType)) {
            throw new EndpointException("No field 'listType' found on class " + getClass().getCanonicalName());
        }

        final HttpExecutor httpExecutor = getSettings().getHttpExecutor();
        final Response response = httpExecutor.execute(request);
        return handleResponse(response, listType);
    }

    protected void performVoidCall(final Request request) {
        if (Objects.isNull(instanceType)) {
            throw new EndpointException("No field 'listType' found on class " + getClass().getCanonicalName());
        }

        final HttpExecutor httpExecutor = getSettings().getHttpExecutor();
        final Response response = httpExecutor.execute(request);
        handleResponse(response, Void.class);
    }

    private <R> R handleResponse(final Response response, final Object type) {
        final R result = deserializeResult(response, type);

        if (response.inRange(_200)) {
            this.getOnSuccess().accept(response, result);
        } else {
            final Object error = deserializeError(response);
            this.getOnError().accept(response, error);
        }

        return result;
    }

    private <R> R deserializeResult(final Response response, final Object type) {
        if (Void.class.equals(type)) {
            return null;
        }

        if (type instanceof Class) {
            return (R) response.getBodyFromJson((Class) type);
        } else {
            return (R) response.getBodyFromJson((Type) type);
        }
    }

    private Object deserializeError(final Response response) {
        if (Objects.isNull(errorType)) {
            throw new EndpointException("No field 'errorType' found on class " + getClass().getCanonicalName());
        }

        try {
            final String body = response.getBody();
            return response.getJsonConverter().toObject(body, errorType);
        } catch (Exception e) {
            throw new EndpointException("Failed to deserialize error response body", e);
        }
    }
}
