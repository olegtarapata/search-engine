package com.search.client.cli;

import com.search.client.SearchServiceClient;
import com.search.service.DocumentAlreadyExistsException;
import com.search.service.DocumentNotExistException;
import com.search.service.SearchDocument;

import java.util.Scanner;
import java.util.UUID;

public class SearchServiceCliClient {

    private static final String DEFAULT_HOST = "localhost";

    private static final int DEFAULT_PORT = 8080;

    private static final String SEARCH_PREFIX = "search ";

    private static final String ADD_PREFIX = "add ";

    private static final String GET_PREFIX = "get ";

    private final SearchServiceClient client;

    public SearchServiceCliClient(final String host, final int port) {
        client = new SearchServiceClient(host, port);
    }

    public static void main(String[] args) {
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            if (args.length != 2) {
                System.out.println("usage: <host> <port>");
            }
            host = args[0];
            port = Integer.parseInt(args[1]);
        }
        new SearchServiceCliClient(host, port).handleInput();
    }

    public void handleInput() {
        client.search(UUID.randomUUID().toString());
        final Scanner scanner = new Scanner(System.in);
        writeUsage();
        while (true) {
            try {
                System.out.print("search-cli> ");
                final String command = scanner.nextLine();
                if (command.isEmpty()) {
                    continue;
                }
                if (command.equals("quit")) {
                    return;
                }
                if (command.startsWith(GET_PREFIX)) {
                    final String key = command.substring(GET_PREFIX.length()).trim();
                    System.out.println(client.getDocument(key).getContent());
                    continue;
                }
                if (command.startsWith(ADD_PREFIX)) {
                    final String keyAndContent = command.substring(ADD_PREFIX.length());
                    final int separationIndex = keyAndContent.indexOf(" ");
                    final String key = keyAndContent.substring(0, separationIndex).trim();
                    final String content = keyAndContent.substring(separationIndex + 1).trim();
                    if (key.isEmpty()) {
                        System.out.println("key is empty");
                        continue;
                    }
                    if (content.isEmpty()) {
                        System.out.println("content is empty");
                        continue;
                    }
                    client.addDocument(new SearchDocument(key, content));
                    continue;
                }
                if (command.startsWith(SEARCH_PREFIX)) {
                    final String query = command.substring(SEARCH_PREFIX.length()).trim();
                    if (query.isEmpty()) {
                        System.out.println("query is empty");
                        continue;
                    }
                    System.out.println(client.search(query));
                    continue;
                }
                System.out.println("invalid command");
                writeUsage();
            } catch (DocumentAlreadyExistsException | DocumentNotExistException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void writeUsage() {
        System.out.println("Available commands:");
        System.out.println("\tget <key> - get document by key");
        System.out.println("\tadd <key> <content> - add document");
        System.out.println("\tsearch <query> - search documents");
        System.out.println("\tquit - quit client");
    }
}
