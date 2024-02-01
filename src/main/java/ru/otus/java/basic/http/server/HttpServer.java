package ru.otus.java.basic.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * В этом коде добавлены логгеры для информации о начале и успешном завершении работы сервера,
 * а также уровни логирования для обработки ошибок и успешных операций.
 */
public class HttpServer {
    private static final Logger LOGGER = LogManager.getLogger(HttpServer.class);

    private int port;
    private Dispatcher dispatcher;
    private ExecutorService threadPool;

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
        this.threadPool = Executors.newFixedThreadPool(10); // Размер пула потоков (в данном случае, 10)
    }

    public void start() {
        LOGGER.info("Starting the HTTP server on port: {}", port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.info("Server is running...");

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    // Используем пул потоков для обработки запроса
                    threadPool.execute(() -> handleClient(socket));
                } catch (IOException e) {
                    LOGGER.error("Error accepting client connection.", e);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error starting the server.", e);
        } finally {
            // Завершаем работу пула потоков при завершении сервера
            threadPool.shutdown();
            LOGGER.info("Server shutdown complete.");
        }
    }

    private void handleClient(Socket socket) {
        try {
            LOGGER.info("Handling client connection...");

            byte[] buffer = new byte[8192];
            int n = socket.getInputStream().read(buffer);
            String rawRequest = new String(buffer, 0, n);
            HttpRequest httpRequest = new HttpRequest(rawRequest);
            // Установите статус код ответа, например, 404 Not Found
            httpRequest.setStatusCode(404);
            dispatcher.execute(httpRequest, socket.getOutputStream());

            LOGGER.info("Client connection handled successfully.");
        } catch (IOException e) {
            LOGGER.error("Error handling client connection.", e);
        } finally {
            try {
                // Закрываем соединение после обработки запроса
                socket.close();
            } catch (IOException e) {
                LOGGER.error("Error closing client socket.", e);
            }
        }
    }
}
