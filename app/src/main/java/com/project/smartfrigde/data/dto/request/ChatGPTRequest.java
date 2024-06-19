package com.project.smartfrigde.data.dto.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatGPTRequest {
    @SerializedName("model")
    private String model;
    @SerializedName("messages")
    private List<Message> messages;

    public ChatGPTRequest(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }

    public static class Message {
        @SerializedName("role")
        private String role;
        @SerializedName("content")
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}

