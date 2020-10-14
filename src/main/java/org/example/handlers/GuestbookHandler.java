package org.example.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.dao.EntryDao;
import org.example.exceptions.ObjectNotFoundException;
import org.example.helpers.Parser;
import org.example.model.Entry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

public class GuestbookHandler implements HttpHandler {

    private ObjectMapper objectMapper;

    public GuestbookHandler() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";

        try {
            switch (method) {
                case "GET":
                    response = get(exchange);
                    sendResponse(exchange, response, 200);
                    break;
                case "POST":
                    response = post(exchange);
                    sendResponse(exchange, response, 200);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
            sendResponse(exchange, response, 404);
        }
    }

    private String get(HttpExchange exchange) throws JsonProcessingException, ObjectNotFoundException {
        return entriesToJSON();
    }

    private String post(HttpExchange exchange) throws IOException, ObjectNotFoundException {

        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
        BufferedReader br = new BufferedReader(isr);

        Map<String, String> data = Parser.parseData(br.readLine());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(data.get("dateAndTime"), formatter);

        Entry entry = new Entry();
        entry.setContent(data.get("content"))
                .setAuthorName(data.get("authorName"))
                .setDateAndTime(localDateTime);

        try {
            EntryDao entryDao = new EntryDao();
            entryDao.addEntry(entry);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entriesToJSON();
    }

    private void sendResponse(HttpExchange exchange, String response, int status) throws IOException {
        if (status == 200) {
            exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));
            exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("http://localhost:63342"));
        }
        exchange.sendResponseHeaders(status, response.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String entriesToJSON() throws JsonProcessingException, ObjectNotFoundException {
        EntryDao entryDao = new EntryDao();
        return objectMapper.writeValueAsString(entryDao.getEntries());
    }
}
