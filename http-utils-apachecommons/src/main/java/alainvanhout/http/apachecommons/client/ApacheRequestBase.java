package alainvanhout.http.apachecommons.client;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

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
