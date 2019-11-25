package com.nowak.SpringBoot.Thymeleaf.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reported_comments")
public class ReportedComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "comment_id")
    private int commentID;

    @Column(name = "reporting_user")
    private String reportingUser;

    private String userNick;
    int userID;
    private int fileID;
    private String comment;
    private String photo;
    private Date date;

    public ReportedComments(int id, int userID, int commentID, String reportingUser, String userNick, int fileID, String comment, String photo, Date date) {
        this.userID = userID;
        this.id = id;
        this.commentID = commentID;
        this.reportingUser = reportingUser;
        this.userNick = userNick;
        this.fileID = fileID;
        this.comment = comment;
        this.photo = photo;
        this.date = date;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public ReportedComments() {
    }

    public ReportedComments(int commentID, String reportingUser) {
        this.commentID = commentID;
        this.reportingUser = reportingUser;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getReportingUser() {
        return reportingUser;
    }

    public void setReportingUser(String reportingUser) {
        this.reportingUser = reportingUser;
    }
}
