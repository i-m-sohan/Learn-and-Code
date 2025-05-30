package com.InventoryConsoleApp.wrapper;

import java.util.HashMap;
import java.util.Map;

public class ApiRequestWrapper {

    private String url;
    private String method;
    private String body;
    private Map<String, String> headers;

    public ApiRequestWrapper(){
        url = "";
        method = "";
        body = "";
        headers = new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "ApiRequestWrapper{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", body='" + body + '\'' +
                ", headers=" + headers +
                '}';
    }
}
