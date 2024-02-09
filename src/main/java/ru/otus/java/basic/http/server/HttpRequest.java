package ru.otus.java.basic.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * В этом коде добавлены логгеры для информации о начале и успешном завершении парсинга HTTP-запроса.
 */
public class HttpRequest {
    private int statusCode = 200; // По умолчанию 200 OK
    private static final Logger LOGGER = LogManager.getLogger(HttpRequest.class);

    private String rawRequest;
    private String uri;
    private HttpMethod method;
    private Map<String, String> parameters;

    public String getUri() {
        return uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        parseRequestLine();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    private void parseRequestLine() {
        LOGGER.info("Parsing HTTP request line...");

        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        this.uri = rawRequest.substring(startIndex + 1, endIndex);
        this.parameters = new HashMap<>();
        if (uri.contains("?")) {
            String[] elements = uri.split("[?]");
            this.uri = elements[0];
            String[] keysValues = elements[1].split("&");
            for (String o : keysValues) {
                String[] keyValue = o.split("=");
                this.parameters.put(keyValue[0], keyValue[1]);
            }
        }
        this.method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));

        LOGGER.info("HTTP request line parsed successfully. URI: {}, Method: {}", uri, method);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public void printInfo(boolean showRawRequest) {
        if (showRawRequest) {
            LOGGER.info(rawRequest);
        }
        LOGGER.info("URI: {}", uri);
        LOGGER.info("HTTP METHOD: {}", method);
    }
}