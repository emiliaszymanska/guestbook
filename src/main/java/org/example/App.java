package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.handlers.GuestbookHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8015), 0);

        server.createContext("/guestbook", new GuestbookHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Server started at " + server.getAddress().getPort());
    }
}
