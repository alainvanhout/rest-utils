package alainvanhout.endpoint.api;

import alainvanhout.http.client.HttpExecutor;
import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;

import java.lang.reflect.Type;

import static alainvanhout.http.common.StatusCodeRange.*;

/**
 *
 * @param <T> Endpoint class itself, for chaining
 * @param <U> The instance type
 * @param <V> The list type
 */
public class Endpoint<T extends Endpoint, U, V> extends CallHandler<T>  {

    private Class<U> instanceType;
    private Type listType;

    public Endpoint() {
        this.instanceType = extractInstanceType();
        this.listType = extractListType();
    }

    public T init(final String url, CallHandler parent){
        this.instanceType = extractInstanceType();
        this.listType = extractListType();
        this.url = url;
        this.parent(parent);
        return (T)this;
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

    protected Request createRequest(){
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
            return (R)response.getBodyFromJson((Class)type);
        } else {
            return (R)response.getBodyFromJson((Type)type);
        }
    }
}
