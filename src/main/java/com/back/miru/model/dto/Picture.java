package com.back.miru.model.dto;

import java.sql.Timestamp;

public class Picture {
    private int pictureIdx;
    private String filepath;
    private String tag;
    private int publicFlag;
    private int isPicture;
    private String id;
    private Timestamp updateTime;

    public Picture(int pictureIdx, String filepath, String tag, int publicFlag, int isPicture, String id, Timestamp updateTime) {
        this.pictureIdx = pictureIdx;
        this.filepath = filepath;
        this.tag = tag;
        this.publicFlag = publicFlag;
        this.isPicture = isPicture;
        this.id = id;
        this.updateTime = updateTime;
    }

    public int getPictureIdx() {
        return pictureIdx;
    }

    public void setPictureIdx(int pictureIdx) {
        this.pictureIdx = pictureIdx;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPublicFlag() {
        return publicFlag;
    }

    public void setPublicFlag(int publicFlag) {
        this.publicFlag = publicFlag;
    }

    public int getIsPicture() {
        return isPicture;
    }

    public void setIsPicture(int isPicture) {
        this.isPicture = isPicture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

}