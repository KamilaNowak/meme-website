package com.nowak.SpringBoot.Thymeleaf.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MemeModel {

    @NotNull(message = "Title cannot be empty")
    @Size(min=1,message = "Required minimum 2 characrers")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
