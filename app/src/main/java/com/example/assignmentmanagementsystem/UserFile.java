package com.example.assignmentmanagementsystem;

import com.google.firebase.database.Exclude;

public class UserFile {
    public String fileName;
    public String fileCourse;
    public String fileStatus;
    public String fileUrl;

    public String mKey;

    public UserFile() {
    }

    public UserFile(String fileName, String fileCourse, String fileStatus, String fileUrl) {
        this.fileName = fileName;
        this.fileCourse = fileCourse;
        this.fileStatus = fileStatus;
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileCourse() {
        return fileCourse;
    }

    public void setFileCourse(String fileCourse) {
        this.fileCourse = fileCourse;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String Key) {
        mKey = Key;
    }
}
