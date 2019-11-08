package com.nowak.SpringBoot.Thymeleaf.models;

import javax.validation.constraints.NotNull;

public class MemeModel {

    @NotNull(message = "Title cannot be empty")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
