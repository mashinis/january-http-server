package ru.otus.java.basic.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.http.server.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * В этом коде добавлены логгеры для информации о начале и успешном завершении обработки запроса,
 * а также предупреждение при обработке неизвестного URI.
 */
public class Dispatcher {
    private static final Logger LOGGER = LogManager.getLogger(Dispatcher.class);

    private Map<String, RequestProcessor> router;
    private RequestProcessor unknownRequestProcessor;

    public Dispatcher() {
        this.router = new HashMap<>();
        this.router.put("/json", new JsonRequestProcessor());
        this.router.put("/add", new OperationAddRequestProcessor());         // /GET /add => OperationAddRequestProcessor
        this.router.put("/hello_world", new HelloWorldRequestProcessor());   // /GET /hello_world => HelloWorldRequestProcessor
        this.unknownRequestProcessor = new UnknownRequestProcessor();
    }

    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        LOGGER.info("Executing request for URI: {}", httpRequest.getUri());

        if (!router.containsKey(httpRequest.getUri())) {
            LOGGER.warn("Unknown URI. Redirecting to UnknownRequestProcessor.");
            unknownRequestProcessor.execute(httpRequest, output);
            return;
        }

        RequestProcessor requestProcessor = router.get(httpRequest.getUri());
        LOGGER.info("Executing request processor: {}", requestProcessor.getClass().getSimpleName());
        requestProcessor.execute(httpRequest, output);

        LOGGER.info("Request processed successfully.");
    }
}
