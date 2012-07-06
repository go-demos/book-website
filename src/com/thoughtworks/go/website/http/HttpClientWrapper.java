package com.thoughtworks.go.website.http;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientWrapper {
    private final String hostname;
    private final int port;
    private HttpMethodFactory factory;
    private HttpClient client;

    public HttpClientWrapper(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.factory = new HttpMethodFactory();
        this.client = new HttpClient();
    }

    public String get(String path) {
        return get(path, new HashMap<String, String>());
    }

    public String get(String path, Map<String, String> params) {
        try {
            HttpURL httpURL = new HttpURL(baseUrl() + path);
            HttpMethod getMethod = methodFor(httpURL, params);

            int returnCode = client.executeMethod(getMethod);

            if (isSuccessful(returnCode)) {
                return getMethod.getResponseBodyAsString();
            }
            throw new RuntimeException(String.format("The request to [%s] could not be completed. Response [%s] was returned with code [%s]", path, getMethod.getResponseBodyAsString(), returnCode));
        } catch (IOException e) {
            throw new RuntimeException("Connection pooped", e);
        }
    }


    private HttpMethod methodFor(HttpURL httpURL, Map<String, String> params) throws URIException {
        HttpMethod getMethod = factory.create(HttpMethodFactory.GET);
        getMethod.setURI(httpURL);
        if (params != null) {
            getMethod.setParams(httpParams(params));
        }
        return getMethod;
    }

    private boolean isSuccessful(int returnCode) {
        return returnCode >= 200 && returnCode < 300;
    }

    private HttpMethodParams httpParams(Map<String, String> params) {
        HttpMethodParams methodParams = new HttpMethodParams();
        for (String param : params.keySet()) {
            methodParams.setParameter(param,  params.get(param));
        }
        return methodParams;
    }

    private String baseUrl() {
        return String.format("http://%s:%d", hostname, port);
    }

}
