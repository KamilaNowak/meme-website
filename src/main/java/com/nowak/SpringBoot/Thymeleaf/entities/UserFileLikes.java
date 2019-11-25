package com.nowak.SpringBoot.Thymeleaf.entities;

import javax.persistence.*;

@Entity
@Table(name="user_file_likes")
public class UserFileLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="id_user")
    private int userId;

    @Column(name="id_file")
    private int fileId;

    private String userNick;

    public UserFileLikes(int userId, int fileId, String userNick) {
        this.userId = userId;
        this.fileId = fileId;
        this.userNick = userNick;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public UserFileLikes() {
    }

    public UserFileLikes(int userId, int fileId) {
        this.userId = userId;
        this.fileId = fileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
