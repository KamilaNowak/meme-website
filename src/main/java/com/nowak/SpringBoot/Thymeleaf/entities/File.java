package com.nowak.SpringBoot.Thymeleaf.entities;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.sun.xml.txw2.annotation.XmlCDATA;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="user_name")
    private String userNick;

    @Column(name ="path")
    private String path;

    @Column(name="title")
    private String title;

    @Column(name="likes")
    private int likes;

    @Column(name="dislikes")
    private int dislikes;

    @Column(name="date",columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    public File() {
    }

    public File(String userNick, String path, String title, int likes, int dislikes, Date data) {
        this.userNick = userNick;
        this.path = path;
        this.title = title;
        this.likes = likes;
        this.dislikes = dislikes;
        this.data = data;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
