package com.nowak.SpringBoot.Thymeleaf.entities;

import org.hibernate.annotations.Columns;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="photo")
    private String photo;

    @Column(name = "confirm_token")
    private String confirmToken;

    @Column(name="enabled")
    private boolean enabled;

    @Column(name="mute",columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mute;

    public Date getMute() {
        return mute;
    }

    public void setMute(Date mute) {
        this.mute = mute;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getConfirmToken() {
        return confirmToken;
    }

    public void setConfirmToken(String confirmToken) {
        this.confirmToken = confirmToken;
    }

    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="accounts_and_roles",joinColumns = @JoinColumn(name="id_name"), inverseJoinColumns = @JoinColumn(name="id_authority"))
    private Collection<Authority>authorities;

    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<Authority> authorities) {
        this.authorities = authorities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
