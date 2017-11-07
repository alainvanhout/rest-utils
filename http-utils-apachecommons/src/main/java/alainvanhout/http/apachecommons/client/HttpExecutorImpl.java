package alainvanhout.http.apachecommons.client;

import alainvanhout.http.HttpException;
import alainvanhout.http.client.HttpExecutorBuilder;
import alainvanhout.http.client.HttpExecutor;
import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URI;

public class HttpExecutorImpl implements HttpExecutor, HttpExecutorBuilder {

    private CloseableHttpClient apacheClient;

    @Override
    public HttpExecutor init() {
        apacheClient = HttpClientBuilder.create().build();
        return this;
    }

    @Override
    public Response execute(final Request request) {
        final ApacheRequestBase apacheRequest = new ApacheRequestBase();

        apacheRequest.setMethod(request.getMethod());
        apacheRequest.setURI(URI.create(request.getUrl()));

        try {
            final CloseableHttpResponse apacheResponse = apacheClient.execute(apacheRequest);

            return convertToResponse(apacheResponse);

        } catch (Exception e) {
            throw new HttpException("Encountered error while executing request: " + request, e);
        }
    }

    private Response convertToResponse(CloseableHttpResponse apacheResponse) {
        final StatusLine statusLine = apacheResponse.getStatusLine();

        return new Response()
                .statusCode(statusLine.getStatusCode());
    }
}
