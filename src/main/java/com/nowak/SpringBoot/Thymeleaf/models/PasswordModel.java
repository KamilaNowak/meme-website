package com.nowak.SpringBoot.Thymeleaf.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordModel {
    @NotNull(message = "Fill the gap!")
    @Size(min = 2, message = "Required minimum 2 characters")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
