package com.nowak.SpringBoot.Thymeleaf.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="user_name")
    private String userNick;

    @Column(name="file_id")
    private int fileID;

    @Column(name="comment")
    private String comment;

    @Column(name="date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Comments() { ;
    }

    public Comments(String userNick, int fileID, String comment, Date date) {
        this.userNick = userNick;
        this.fileID = fileID;
        this.comment = comment;
        this.date = date;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
