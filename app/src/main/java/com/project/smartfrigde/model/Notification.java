package com.project.smartfrigde.model;

public class Notification {
    private String time; // Thời gian
    private String title; // Tiêu đề
    private String content; // Nội dung

    public Notification(String time, String title, String content) {
        this.time = time;
        this.title = title;
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}