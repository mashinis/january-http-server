package ru.otus.java.basic.http.server.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * В приведенном коде добавлены логгеры для информации о начале
 * и успешном завершении обработки запроса,
 * а также для ошибок при обработке запроса.
 */
public class OperationAddRequestProcessor implements RequestProcessor {
    private static final Logger LOGGER = LogManager.getLogger(OperationAddRequestProcessor.class);

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        LOGGER.info("Processing OperationAddRequestProcessor...");

        try {
            int a = Integer.parseInt(httpRequest.getParameter("a"));
            int b = Integer.parseInt(httpRequest.getParameter("b"));
            int result = a + b;

            httpRequest.setStatusCode(200);
            String response = "HTTP/1.1 " + httpRequest.getStatusCode() + " OK\r\nContent-Type: text/html\r\n\r\n<html><body><h1>" + a + " + " + b + " = " + result + "</h1></body></html>";
            output.write(response.getBytes(StandardCharsets.UTF_8));

            LOGGER.info("OperationAddRequestProcessor processed successfully. Result: {}", result);
        } catch (NumberFormatException e) {
            LOGGER.error("Error processing OperationAddRequestProcessor. Invalid parameters.", e);

            httpRequest.setStatusCode(400);
            String errorResponse = "HTTP/1.1 " + httpRequest.getStatusCode() + " Bad Request\r\nContent-Type: text/plain\r\n\r\nInvalid parameters";
            output.write(errorResponse.getBytes(StandardCharsets.UTF_8));
        }
    }
}
