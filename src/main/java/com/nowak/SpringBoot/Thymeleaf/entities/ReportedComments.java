package com.nowak.SpringBoot.Thymeleaf.entities;

import javax.persistence.*;

@Entity
@Table(name="reported_comments")
public class ReportedComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="comment_id")
    private int commentID;

    @Column(name = "reporting_user")
    private String reportingUser;

    public ReportedComments() {
    }

    public ReportedComments(int commentID, String reportingUser) {
        this.commentID = commentID;
        this.reportingUser = reportingUser;
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
