package ru.otus.java.basic.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Урок 31
 * ДЗ 23: Доработка простого “веб” приложения
 * Реализуйте возможность обработки более одного потока за запуск сервера
 * Реализуйте обработку запросов в отдельных потоках
 */
public class MainApplication {
    private static final Logger LOGGER = LogManager.getLogger(MainApplication.class);

    // Домашнее задание:
    // - Добавить логирование
    // - Добавить обработку запросов в параллельных потоках

    public static void main(String[] args) {
        LOGGER.info("Starting the HTTP server...");

        HttpServer server = new HttpServer(Integer.parseInt((String) System.getProperties().getOrDefault("port", "8189")));
        server.start();

        LOGGER.info("HTTP server started.");
    }
}


/**
 * HTTP/1.1 200 OK
 * Content-Type: text/html
 *
 * <html>
 *     <body>
 *         <h1>Hello World!</h1>
 *     </body>
 * </html>
 */