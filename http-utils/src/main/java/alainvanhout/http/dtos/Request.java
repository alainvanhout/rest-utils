package alainvanhout.http.dtos;

public class Request {
    private String url;
    private String method;
    private String body;

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public Request url(final String url) {
        this.url = url;
        return this;
    }

    public Request method(final String method) {
        this.method = method;
        return this;
    }

    public Request body(final String body) {
        this.body = body;
        return this;
    }
}
