package com.nowak.SpringBoot.Thymeleaf.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmailModel {

    @NotNull(message = "Fill the gap!")
    @Size(min = 2, message = "Required valid e-mail format")
    @Email(message = "Please provide a valid e-mail")
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
