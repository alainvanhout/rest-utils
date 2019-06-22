package alainvanhout.http.apachecommons.client;

import alainvanhout.http.HttpException;
import alainvanhout.http.client.HttpExecutor;
import alainvanhout.http.client.HttpExecutorBuilder;
import alainvanhout.http.dtos.Request;
import alainvanhout.http.dtos.Response;
import alainvanhout.http.parameters.Parameters;
import alainvanhout.http.parameters.ParametersUtility;
import alainvanhout.json.JsonConverter;
import alainvanhout.json.JsonDefaults;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpExecutorImpl implements HttpExecutor, HttpExecutorBuilder {

    private CloseableHttpClient apacheClient;

    private JsonConverter jsonConverter = null;

    private Integer timeout = 60_000;
    private int maxConnections = 100;

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
    public HttpExecutorImpl timeout(final Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    @Override
    public HttpExecutorImpl maxConnections(final int maxConnections) {
        this.maxConnections = maxConnections;
        return this;
    }

    @Override
    public HttpExecutor init() {
        PoolingHttpClientConnectionManager connectionManager = buildConnectionManager();

        apacheClient = HttpClientBuilder
            .create()
            .setConnectionManager(connectionManager)
            .build();

        return this;
    }

    private PoolingHttpClientConnectionManager buildConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxConnections);
        connectionManager.setDefaultMaxPerRoute(maxConnections);
        return connectionManager;
    }

    @Override
    public Response execute(final Request request) {
        final ApacheRequestBase apacheRequest = new ApacheRequestBase();

        final String[] split = StringUtils.split(request.getUrl(), "?");
        final String minimalUrl = split[0];
        final String queryString = split.length > 1 ? split[1] : "";

        final Parameters urlParameters = buildFinalParameters(queryString, request.getParameters());
        final String url = minimalUrl + buildParameters(urlParameters);
        final RequestConfig requestConfig = buildRequestConfig(request);

        apacheRequest.setConfig(requestConfig);
        apacheRequest.setMethod(request.getMethod());
        apacheRequest.setURI(URI.create(url));
        applyHeaders(apacheRequest, request);
        applyBody(apacheRequest, request);

        final long startTime = System.currentTimeMillis();
        try {
            try (final CloseableHttpResponse apacheResponse = apacheClient.execute(apacheRequest)) {
                final long endTime = System.currentTimeMillis();

                Response response = convertToResponse(apacheResponse);
                response.duration(endTime - startTime);

                assignJsonConverterToResponse(request, response);

                return response;
            }
        } catch (ConnectTimeoutException | SocketTimeoutException  e) {
            final long endTime = System.currentTimeMillis();
            return new Response()
                .timedOut(true)
                .duration(endTime - startTime)
                .statusCode(0);
        } catch (Exception e) {
            throw new HttpException("Encountered error while executing request: " + request, e);
        }
    }

    private RequestConfig buildRequestConfig(Request request) {
        Integer concreteTimeout = request.getTimeout() != null ? request.getTimeout() : this.timeout;
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        if (concreteTimeout != null) {
            configBuilder
                .setConnectionRequestTimeout(concreteTimeout)
                .setConnectTimeout(concreteTimeout)
                .setSocketTimeout(concreteTimeout);
        }
        return configBuilder.build();
    }

    private void applyBody(ApacheRequestBase apacheRequest, Request request) {
        if (Objects.nonNull(request.getBody())) {
            try {
                apacheRequest.setEntity(new StringEntity(request.getBody()));
            } catch (UnsupportedEncodingException e) {
                throw new HttpException("Unable to attach body to request: " + request, e);
            }
        }
    }

    private void assignJsonConverterToResponse(Request request, Response response) {
        final JsonConverter requestJsonConverter = request.getJsonConverter();
        // there is an order of precedence for which json converter is assigned:
        // request > http executor > global default
        if (Objects.nonNull(requestJsonConverter)) {
            response.jsonConverter(requestJsonConverter);
        } else if (Objects.nonNull(this.jsonConverter)) {
            response.jsonConverter(this.jsonConverter);
        } else {
            // note that the default may yet be null
            response.jsonConverter(JsonDefaults.getDefaultJsonConverter());
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
            headers.add(header.getName(), header.getValue());
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
