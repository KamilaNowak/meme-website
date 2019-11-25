package com.nowak.SpringBoot.Thymeleaf.entities;

import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="reported")
public class Reported {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="file_id")
    private int fileID;

    @Column(name="reporting_user")
    private String reportingUser;

    private int userID;
    private Date addedDate;
    private String filePath;
    private String addingUser;
    private String fileTitle;

    public Reported() {
    }

    public Reported(int id,int userID, int fileID,String reportingUser, Date addedDate, String filePath, String addingUser,String fileTitle) {
        this.userID = userID;
        this.id=id;
        this.fileID=fileID;
        this.reportingUser = reportingUser;
        this.addedDate = addedDate;
        this.filePath = filePath;
        this.addingUser = addingUser;
        this.fileTitle= fileTitle;
    }


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAddingUser() {
        return addingUser;
    }

    public void setAddingUser(String addingUser) {
        this.addingUser = addingUser;
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

    public String getReportingUser() {
        return reportingUser;
    }

    public void setReportingUser(String reportingUser) {
        this.reportingUser = reportingUser;
    }
}
