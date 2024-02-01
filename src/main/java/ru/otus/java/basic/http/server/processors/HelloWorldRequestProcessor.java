package ru.otus.java.basic.http.server.processors;

import ru.otus.java.basic.http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * В код добавлены два вызова LOGGER.info() для записи информации о начале
 * и успешном завершении обработки запроса. Уровень логирования info()
 */
public class HelloWorldRequestProcessor implements RequestProcessor {
    private static final Logger LOGGER = LogManager.getLogger(HelloWorldRequestProcessor.class);

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        LOGGER.info("Processing HelloWorldRequestProcessor...");

        httpRequest.setStatusCode(200);
        String response = "HTTP/1.1 " + httpRequest.getStatusCode() + " OK\r\nContent-Type: text/html\r\n\r\n<html><body><h1>Hello World!</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));

        LOGGER.info("HelloWorldRequestProcessor processed successfully.");
    }
}
