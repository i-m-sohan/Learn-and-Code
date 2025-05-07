package wrapper;

import constant.GeocodingConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ApiRequestWrapper {
    private final static String UTF_8 = "UTF-8";
    private final static String API_KEY = "api_key";

    private String method;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> queryParams = new HashMap<>();


    public void setMethod(String method){
        this.method = method;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addQueryParam(String key, String value) {
        queryParams.put(key, value);
    }

    public String getUrl() throws UnsupportedEncodingException {
        StringBuilder fullUrl = new StringBuilder(GeocodingConstants.BASE_URL);
        if (!queryParams.isEmpty()) {
            fullUrl.append("?");
            for (Map.Entry<String, String> param : queryParams.entrySet()) {
                fullUrl.append(URLEncoder.encode(param.getKey(), UTF_8))
                        .append("=")
                        .append(URLEncoder.encode(param.getValue(), UTF_8))
                        .append("&");
            }
            fullUrl.append(API_KEY).append("=").append(URLEncoder.encode(GeocodingConstants.GEOCODING_API_KEY_VALUE, UTF_8));
        }
        return fullUrl.toString();
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}