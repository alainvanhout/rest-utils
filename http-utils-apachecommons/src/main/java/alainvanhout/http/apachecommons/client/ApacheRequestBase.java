package alainvanhout.http.apachecommons.client;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

/**
 * Provides an easy way to create Apache Commons requests with a given http method/verb.
 */
public class ApacheRequestBase extends HttpEntityEnclosingRequestBase {

    private String method;

    public ApacheRequestBase() {
        super();
    }

    @Override
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
