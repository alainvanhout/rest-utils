package alainvanhout.http.apachecommons.client;

import alainvanhout.http.HttpDefaults;
import alainvanhout.http.HttpException;
import alainvanhout.http.client.HttpExecutor;
import alainvanhout.http.client.HttpExecutorBuilder;
import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;
import alainvanhout.http.parameters.Parameters;
import alainvanhout.http.parameters.ParametersUtility;
import alainvanhout.json.JsonConverter;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpExecutorImpl implements HttpExecutor, HttpExecutorBuilder {

    private CloseableHttpClient apacheClient;

    private JsonConverter jsonConverter = null;

    private HttpExecutorImpl() {
    }

    public static HttpExecutorBuilder with() {
        return new HttpExecutorImpl();
    }

    @Override
    public HttpExecutorImpl jsonConverter(final JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
        return this;
    }

    @Override
    public HttpExecutor init() {
        apacheClient = HttpClientBuilder.create().build();
        return this;
    }

    @Override
    public Response execute(final Request request) {
        final ApacheRequestBase apacheRequest = new ApacheRequestBase();

        final String[] split = StringUtils.split(request.getUrl(), "?");
        final String minimalUrl = split[0];
        final String queryString = split.length > 1 ? split[1] : "";

        final Parameters urlParameters = buildFinalParameters(queryString, request.getParameters());
        final String url = minimalUrl + buildParameters(urlParameters);

        apacheRequest.setMethod(request.getMethod());
        apacheRequest.setURI(URI.create(url));
        applyHeaders(apacheRequest, request);

        try {
            final long startTime = System.currentTimeMillis();
            final CloseableHttpResponse apacheResponse = apacheClient.execute(apacheRequest);
            final long endTime = System.currentTimeMillis();

            Response response = convertToResponse(apacheResponse);
            response.setDuration(endTime - startTime);

            assignJsonConverrterToResponse(request, response);

            return response;

        } catch (Exception e) {
            throw new HttpException("Encountered error while executing request: " + request, e);
        }
    }

    private void assignJsonConverrterToResponse(Request request, Response response) {
        final JsonConverter requestJsonConverter = request.getJsonConverter();
        // there is an order of precedence for which json converter is assigned:
        // request > http executor > global default
        if (Objects.nonNull(requestJsonConverter)) {
            response.jsonConverter(requestJsonConverter);
        } else if (Objects.nonNull(this.jsonConverter)) {
            response.jsonConverter(this.jsonConverter);
        } else {
            // note that the default may yet be null
            response.jsonConverter(HttpDefaults.getDefaultJsonConverter());
        }
    }

    private Parameters buildFinalParameters(String queryString, Parameters requestParameters) {
        final Parameters urlParameters = ParametersUtility.fromQueryString(queryString);
        urlParameters.add(requestParameters);
        return urlParameters;
    }

    private void applyHeaders(ApacheRequestBase apacheRequest, Request request) {
        Map<String, List<String>> map = request.getHeaders().getMap();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String value = entry.getValue().stream().collect(Collectors.joining(";"));
            apacheRequest.addHeader(entry.getKey(), value);
        }
    }

    private String buildParameters(Parameters parameters) {
        if (parameters.isEmpty()) {
            return "";
        }
        return "?" + ParametersUtility.toQueryString(parameters);
    }

    private Response convertToResponse(CloseableHttpResponse apacheResponse) {
        final StatusLine statusLine = apacheResponse.getStatusLine();
        final HttpEntity entity = apacheResponse.getEntity();

        return new Response()
                .statusCode(statusLine.getStatusCode())
                .body(bodyToString(entity))
                .headers(extractHeaders(apacheResponse));
    }

    private Parameters extractHeaders(CloseableHttpResponse apacheResponse) {
        Parameters headers = new Parameters();
        for (Header header : apacheResponse.getAllHeaders()) {
            for (HeaderElement headerElement : header.getElements()) {
                headers.add(header.getName(), headerElement.getName());
            }
        }
        return headers;
    }

    private String bodyToString(HttpEntity entity) {
        try {
            return IOUtils.toString(entity.getContent(), Charsets.UTF_8);
        } catch (IOException | UnsupportedOperationException e) {
            throw new HttpException("Failed to extract body from response", e);
        }
    }
}
