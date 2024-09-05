package org.example;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

public class SocketIoClient {

    private Socket socket;
    DDA a = new DDA();
    Getenv env = new Getenv();

    public void connectTOserver() throws URISyntaxException {
        System.out.println("Attempting to connect to server...");

        // Configure Socket.IO options (optimize reconnection settings for stability)
        IO.Options opts = IO.Options.builder()
                .setTimeout(30000) // Set timeout to 30 seconds to handle slow connections
                .setReconnection(true) // Enable reconnection
                .setReconnectionAttempts(Integer.MAX_VALUE) // Allow unlimited reconnections
                .setReconnectionDelay(5000) // Set delay between reconnections (5 seconds)
                .setReconnectionDelayMax(15000) // Max delay between reconnections (15 seconds)
                .build();

        try {
            // Initialize the socket connection with the options
            socket = IO.socket("http://localhost:8003", opts);
        } catch (URISyntaxException e) {
            System.err.println("Invalid server URI");
            throw e;
        }

        // Register event listeners
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Socket connected to server.");
                sendInitialMessage();
            }
        });
        socket.on("ownerCheck", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject object = (JSONObject) args[0];
                try {
                    a.setPath(object);
                    if (object.getBoolean("matched")) {
                        a.showPopup(a);
                    }
                    System.out.println("File path: " + object.getString("filePath"));
                    System.out.println("Matched: " + object.getBoolean("matched"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Handle connection errors
        socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.err.println("Connection error: " + args[0]);
            }
        });

        // Handle reconnection attempts

        // Handle disconnects
        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Disconnected from server. Reason: " + args[0]);
            }
        });

        // Handle incoming messages
        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Received message: " + args[0]);
            }
        });

        // Connect the socket after registering the event listeners
        socket.connect();
        System.out.println("Socket connection attempt made.");
    }

    private void sendInitialMessage() {
        JSONObject object = new JSONObject();
        String[] paths = {"C:/Users/PCLP/Downloads", "C:/Users/PCLP/Desktop"};

        try {
            object.put("username", env.getusernName());
            object.put("path", env.getPath());
        } catch (JSONException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        sendMessage(object); // Send the initial message
        socket.send("connected successfully");
    }

    public void sendMessage(JSONObject message) {
        socket.emit("message", message);
    }

    public static void main(String[] args) throws URISyntaxException {
        SocketIoClient client = new SocketIoClient();
        client.connectTOserver();
    }
}
