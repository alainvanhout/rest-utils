package alainvanhout.endpoint.api;

import alainvanhout.http.client.HttpExecutor;
import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;
import alainvanhout.json.JsonConverter;

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
            return null;
        }
    }

    /**
     * To be used as a basis for performing calls against this particular endpoint.
     *
     * @return A {@link Request} with minimal endpoint information already applied
     */
    protected Request createRequest() {
        final Request request = new Request()
                .url(getUrl());

        final Settings actualSettings = getSettings();
        final JsonConverter jsonConverter = actualSettings.getJsonConverter();
        final String username = actualSettings.getUsername();

        if (Objects.nonNull(jsonConverter)){
            request.jsonConverter(jsonConverter);
        }

        if (Objects.nonNull(username)) {
            request.basicAuthentication(username, actualSettings.getPassword());
        }

        return request;
    }

    protected U performInstanceCall(final Request request, final Class<U> type) {
        final HttpExecutor httpExecutor = getSettings().getHttpExecutor();
        final Response response = httpExecutor.execute(request);
        return handleResponse(response, type);
    }

    protected U performInstanceCall(final Request request) {
        if (Objects.isNull(instanceType)) {
            throw new EndpointException("No field 'instanceType' found on class " + getClass().getCanonicalName());
        }

        return performInstanceCall(request, instanceType);
    }

    protected V performListCall(final Request request) {
        if (Objects.isNull(listType)) {
            throw new EndpointException("No field 'listType' found on class " + getClass().getCanonicalName());
        }

        return performListCall(request, listType);
    }

    protected V performListCall(final Request request, final Type type) {
        final HttpExecutor httpExecutor = getSettings().getHttpExecutor();
        final Response response = httpExecutor.execute(request);
        return handleResponse(response, type);
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
        if (response.inRange(_200)) {
            final R result = deserializeResult(response, type);
            this.getOnSuccess().accept(response, result);
            return result;
        } else {
            final Object error = deserializeError(response);
            this.getOnError().accept(response, error);
            return null;
        }
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
        final Type actualErrorType = getErrorType();

        try {
            final String body = response.getBody();
            return response.getJsonConverter().toObject(body, Objects.nonNull(actualErrorType) ? actualErrorType : HashMap.class);
        } catch (Exception e) {
           return null;
        }
    }

    Type getErrorType() {
        if (Objects.nonNull(errorType)) {
            return errorType;
        }
        if (this.parent instanceof Endpoint) {
            final Type parentErrorType = ((Endpoint) parent).getErrorType();
            if (Objects.nonNull(parentErrorType)) {
                return parentErrorType;
            }
        }
        if (this.parent instanceof BasicApi) {
            final Type parentErrorType = ((BasicApi) parent).defaultErrorType();
            if (Objects.nonNull(parentErrorType)) {
                return parentErrorType;
            }
        }
        return null;
    }
}
