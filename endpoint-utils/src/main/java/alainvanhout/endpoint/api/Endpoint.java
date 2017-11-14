package alainvanhout.endpoint.api;

import alainvanhout.http.client.HttpExecutor;
import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;

import java.lang.reflect.Type;

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
     * Creating an instance of an {@link Endpoint} class will automatically cause extraction of the instanceType
     * and listType information from the concrete class, as explained in other places of this class' Javadoc.
     */
    public Endpoint() {
        this.instanceType = extractInstanceType();
        this.listType = extractListType();
    }

    public T init(final String url, CallHandler parent) {
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
            throw new EndpointException("No field 'listType' found on class" + getClass().getCanonicalName());
        }
    }

    private Type extractListType() {
        try {
            return this.getClass().getDeclaredField("listType").getGenericType();
        } catch (NoSuchFieldException e) {
            throw new EndpointException("No field 'listType' found on class" + getClass().getCanonicalName());
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

    protected U performInstanceCall(Request request) {
        final HttpExecutor httpExecutor = getSettings().getHttpExecutor();
        final Response response = httpExecutor.execute(request);
        return handleResponse(response, instanceType);
    }

    protected V performListCall(Request request) {
        final HttpExecutor httpExecutor = getSettings().getHttpExecutor();
        final Response response = httpExecutor.execute(request);
        return handleResponse(response, listType);
    }

    private <R> R handleResponse(Response response, Object type) {
        if (response.inRange(_200)) {
            this.getOnSuccess().accept(response);
        } else {
            this.getOnError().accept(response);
        }

        if (type instanceof Class) {
            return (R) response.getBodyFromJson((Class) type);
        } else {
            return (R) response.getBodyFromJson((Type) type);
        }
    }
}
