package com.nowak.SpringBoot.Thymeleaf.entities;

import org.springframework.stereotype.Controller;

import javax.persistence.*;

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
