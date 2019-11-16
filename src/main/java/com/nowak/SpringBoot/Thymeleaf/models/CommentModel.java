package com.nowak.SpringBoot.Thymeleaf.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentModel {

    @NotNull
    @Size(min = 1)
    public String comment;

    int fileID;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }
}
