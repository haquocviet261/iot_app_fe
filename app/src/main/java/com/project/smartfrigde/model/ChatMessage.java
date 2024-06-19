package com.project.smartfrigde.model;

public class ChatMessage {
    private int message_type;
    private String content;
    public ChatMessage() {
    }

    public ChatMessage(int message_type, String content) {
        this.message_type = message_type;
        this.content = content;
    }

    public ChatMessage(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMessage_type() {
        return message_type;
    }

    public void setMessage_type(int message_type) {
        this.message_type = message_type;
    }
}
