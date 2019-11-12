package com.nowak.SpringBoot.Thymeleaf.entities;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.sun.xml.txw2.annotation.XmlCDATA;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="user_id")
    private int userID;

    @Column(name ="path")
    private String path;

    @Column(name="title")
    private String title;

    @Column(name="likes")
    private String likes;

    public File() {
    }

    public File(String path, String title, String likes) {
        this.path = path;
        this.title = title;
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
