package com.ai.domain.loveLittle;

import com.ai.domain.file.File;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.List;

public class LoveLittle {
    private int littleId;
    private int foundUserId;
    private String title;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastModifyTime;
    private Long loveNumber;
    private int important;
    private int type;
    private int status = 0;
    private int result = 0;
    private List<File> fileInfo;

    public static final int PUBLISH = 0;
    public static final int CONFIRM = 1;

    public int getLittleId() {
        return littleId;
    }

    public void setLittleId(int littleId) {
        this.littleId = littleId;
    }

    public int getFoundUserId() {
        return foundUserId;
    }

    public void setFoundUserId(int foundUserId) {
        this.foundUserId = foundUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Long getLoveNumber() {
        return loveNumber;
    }

    public void setLoveNumber(Long loveNumber) {
        this.loveNumber = loveNumber;
    }

    public int getImportant() {
        return important;
    }

    public void setImportant(int important) {
        this.important = important;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<File> getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(List<File> fileInfo) {
        this.fileInfo = fileInfo;
    }
}
