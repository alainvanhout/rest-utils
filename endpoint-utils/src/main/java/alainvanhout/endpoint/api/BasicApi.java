package alainvanhout.endpoint.api;

import alainvanhout.http.dtos.Response;

import java.util.function.Consumer;

public abstract class BasicApi<T extends CallHandler> extends CallHandler<T> {

    public BasicApi(String url, Settings settings) {
        this.url = url;
        this.setSetting(settings);
        this.setOnSuccess(defaultOnSuccess());
        this.setOnError(defaultOnError());
    }

    protected Consumer<Response> defaultOnSuccess() {
        return response -> {};
    }

    protected Consumer<Response> defaultOnError(){
        return response -> {};
    }
}
