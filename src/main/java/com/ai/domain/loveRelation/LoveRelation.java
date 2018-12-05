package com.ai.domain.loveRelation;

public class LoveRelation {
    private int id;
    private int myUserId;
    private int loveUserId;
    private String myLoveLock;
    private String loveLock;
    private Long myloveNumber;
    private Long loveNumber;
    private Long totalNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(int myUserId) {
        this.myUserId = myUserId;
    }

    public int getLoveUserId() {
        return loveUserId;
    }

    public void setLoveUserId(int loveUserId) {
        this.loveUserId = loveUserId;
    }

    public String getMyLoveLock() {
        return myLoveLock;
    }

    public void setMyLoveLock(String myLoveLock) {
        this.myLoveLock = myLoveLock;
    }

    public String getLoveLock() {
        return loveLock;
    }

    public void setLoveLock(String loveLock) {
        this.loveLock = loveLock;
    }

    public Long getLoveNumber() {
        return loveNumber;
    }

    public void setLoveNumber(Long loveNumber) {
        this.loveNumber = loveNumber;
    }

    public Long getMyloveNumber() {
        return myloveNumber;
    }

    public void setMyloveNumber(Long myloveNumber) {
        this.myloveNumber = myloveNumber;
    }

    public Long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Long totalNumber) {
        this.totalNumber = totalNumber;
    }
}
