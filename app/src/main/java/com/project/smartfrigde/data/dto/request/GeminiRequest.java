package com.project.smartfrigde.data.remote.dto.request;

public class GeminiRequest {
    private Document document;
    private String encodingType;

    public GeminiRequest(Document document, String encodingType) {
        this.document = document;
        this.encodingType = encodingType;
    }

    public static class Document {
        private String type;
        private String content;

        public Document(String type, String content) {
            this.type = type;
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
