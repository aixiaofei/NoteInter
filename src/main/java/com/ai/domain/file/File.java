package com.ai.domain.file;

public class File {
    private int fileId;
    private int fileType;
    private int fileSource;
    private int fileSourceId;
    private String fileName;
    private String fileKey;
    private String fileUrl;
    private int fileDirectory;

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public int getFileSource() {
        return fileSource;
    }

    public void setFileSource(int fileSource) {
        this.fileSource = fileSource;
    }

    public int getFileSourceId() {
        return fileSourceId;
    }

    public void setFileSourceId(int fileSourceId) {
        this.fileSourceId = fileSourceId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(int fileDirectory) {
        this.fileDirectory = fileDirectory;
    }
}
