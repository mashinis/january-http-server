package ru.otus.java.basic.http.server.processors;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.http.server.HttpRequest;
import ru.otus.java.basic.http.server.MyJsonObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Преобразовывает объект MyJsonObject к объекту JSON и передает
 * в тело http ответа
 */
public class JsonRequestProcessor implements RequestProcessor {
    private static final Logger LOGGER = LogManager.getLogger(JsonRequestProcessor.class);

    MyJsonObject myObject = new MyJsonObject("value1", "value2");
    String jsonResponse = new Gson().toJson(myObject);

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        LOGGER.info("Processing HelloWorldRequestProcessor...");

        httpRequest.setStatusCode(200);
        String response = "HTTP/1.1 " + httpRequest.getStatusCode() + " OK\r\nContent-Type: application/json\r\n\r\n" + jsonResponse;
        output.write(response.getBytes(StandardCharsets.UTF_8));

        LOGGER.info("JsonRequestProcessor processed successfully.");
    }
}
