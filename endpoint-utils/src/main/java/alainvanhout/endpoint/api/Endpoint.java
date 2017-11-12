package alainvanhout.endpoint.api;

import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;

import java.lang.reflect.Type;

/**
 *
 * @param <T> Endpoint class itself, for chaining
 * @param <U> The instance type
 * @param <V> The list type
 */
public class Endpoint<T extends Endpoint, U, V> {

    protected String url;
    protected Settings settings;

    private Class<U> instanceType;
    private Type listType;

    public T init(final String url, final Settings settings){
        this.url = url;
        this.settings = settings;
        this.instanceType = extractInstanceType();
        this.listType = extractListType();
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
                .url(url);
    }

    protected U performInstanceCall(Request request) {
        final Response response = settings.getHttpExecutor().execute(request);
        return (U)response.getBodyFromJson(instanceType);
    }

    protected V performListCall(Request request) {
        final Response response = settings.getHttpExecutor().execute(request);
        return (V)response.getBodyFromJson(listType);
    }
}
