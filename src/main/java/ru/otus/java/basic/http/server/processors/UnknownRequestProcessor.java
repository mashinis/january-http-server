package ru.otus.java.basic.http.server.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * В этом коде добавлены логгеры для информации о начале
 * и успешном завершении обработки запроса с предупреждением (warn)
 */
public class UnknownRequestProcessor implements RequestProcessor {
    private static final Logger LOGGER = LogManager.getLogger(UnknownRequestProcessor.class);

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        LOGGER.warn("Processing UnknownRequestProcessor...");

        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=utf-8\r\n\r\n<html><body><h1>Получен неизвестный запрос</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));

        LOGGER.warn("UnknownRequestProcessor processed.");
    }
}
