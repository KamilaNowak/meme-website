package com.nowak.SpringBoot.Thymeleaf.entities;

import javax.persistence.*;

@Entity
@Table(name="cubby")
public class Cubby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="email")
    private String email;

    @Column(name="file_id")
    private int fileID;

    private String userNick;
    private String path;

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Cubby() {
    }

    public Cubby(String email, int fileID, String userNick, String path) {
        this.email = email;
        this.fileID = fileID;
        this.userNick = userNick;
        this.path = path;
    }

    public Cubby(String email, int fileID) {
        this.email = email;
        this.fileID = fileID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }
}
