package com.nowak.SpringBoot.Thymeleaf.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AccountModel {

    private int id;

    @NotNull(message = "Fill the gap!")
    @Size(min=2,message = "Required minimum 2 characters")
    private String name;

//    @NotNull(message = "Fill the gap!")
//    @Size(min=2,message = "Required minimum 2 characters")
    private String password;

    @NotNull(message = "Fill the gap!")
    @Size(min=2,message = "Required valid e-mail format")
    @Email(message = "Please provide a valid e-mail")
    private String email;


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
}
