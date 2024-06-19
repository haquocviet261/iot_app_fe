package com.project.smartfrigde.data.remote.websocket;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.Response;
import okio.ByteString;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class WebSocketClient extends WebSocketListener {

    private OkHttpClient client;
    private WebSocket webSocket;
    private Gson gson = new Gson();

    public void start() {
        client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("ws://haquocviet261.click:9999/handle")
                .build();

        webSocket = client.newWebSocket(request, this);
        client.dispatcher().executorService().shutdown();
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        System.out.println("WebSocket Opened");

        // Gửi tin nhắn dạng JSON đến server
        JsonObject message = new JsonObject();
        message.addProperty("type", "greeting");
        message.addProperty("content", "Hello, Server!");
        sendMessage(message);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        System.out.println("Receiving : " + text);

        // Nhận và xử lý dữ liệu JSON từ server
        JsonObject jsonObject = gson.fromJson(text, JsonObject.class);
        handleIncomingMessage(jsonObject);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
        System.out.println("Receiving bytes : " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
        webSocket.close(1000, null);
        System.out.println("Closing : " + code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        t.printStackTrace();
        System.out.println("Error : " + t.getMessage());
    }

    public void sendMessage(JsonObject message) {
        String jsonString = gson.toJson(message);
        webSocket.send(jsonString);
    }

    private void handleIncomingMessage(JsonObject message) {
        // Xử lý dữ liệu JSON nhận được
        System.out.println("Received JSON: " + message.toString());
    }

    public void close() {
        if (webSocket != null) {
            webSocket.close(1000, "Client closed");
        }
    }
}
