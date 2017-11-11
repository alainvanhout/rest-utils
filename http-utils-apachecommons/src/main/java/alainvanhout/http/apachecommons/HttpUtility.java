package alainvanhout.http.apachecommons;

import alainvanhout.http.apachecommons.client.HttpExecutorImpl;
import alainvanhout.http.client.HttpExecutor;
import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;

public class HttpUtility {

    public static HttpExecutor DEFAULT = HttpExecutorImpl.with().init();

    public static Response execute(final Request request){
        return DEFAULT.execute(request);
    }
}
