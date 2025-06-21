package com.example.workleap.data.model.entity;

public class Attachment {
    private String id;
    private String messageId;
    private String filePath;
    private String fileType;
    private String uploaded_at;
    private Message Message;
    public Attachment() {}

    public Attachment(String messageId, String filePath, String fileType, String uploaded_at) {
        this.messageId = messageId;
        this.filePath = filePath;
        this.fileType = fileType;
        this.uploaded_at = uploaded_at;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public String getUploaded_at() { return uploaded_at; }
    public void setUploaded_at(String uploaded_at) { this.uploaded_at = uploaded_at; }
    public void setMessage(Message message) { Message = message; }
    public Message getMessage() { return Message; }
}

