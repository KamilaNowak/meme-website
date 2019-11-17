package com.nowak.SpringBoot.Thymeleaf.controllers;

import com.nowak.SpringBoot.Thymeleaf.entities.Account;
import com.nowak.SpringBoot.Thymeleaf.entities.Comments;
import com.nowak.SpringBoot.Thymeleaf.entities.File;
import com.nowak.SpringBoot.Thymeleaf.models.AccountModel;
import com.nowak.SpringBoot.Thymeleaf.models.CommentModel;
import com.nowak.SpringBoot.Thymeleaf.models.MemeModel;
import com.nowak.SpringBoot.Thymeleaf.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private AppService appService;

    @GetMapping("/")
    public String showMain(Model model) {
        List<File> files = appService.findAll();
        List<Comments> comments = appService.findAllComments();
        for (Comments i : comments) {
            String nick = i.getUserNick();
            String photo = appService.findByName(nick).getPhoto();
            System.out.println(photo);
            i.setPhoto(photo);
        }
        model.addAttribute("files", files);
        model.addAttribute("cmtModel", new CommentModel());
        model.addAttribute("comments", comments);
        return "main-page";
    }

    @GetMapping("/error")
    public String showErrorPage() {
        return "error-page";
    }

    @GetMapping("/add-meme")
    public String showUploadMemePage(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("memeModel", new MemeModel());
        return "upload-meme";
    }

    @GetMapping("/account")
    public String showAccountPage(Model model) {
        List<File> userFiles = appService.findAllByUserNick(appService.getLoggedAccount().getName());
        model.addAttribute("account", appService.getLoggedAccount());
        model.addAttribute("userFiles", userFiles);
        return "account-page";
    }

    @GetMapping("/account/edit")
    public String editAccount(Model model) {
        model.addAttribute("account", new AccountModel());
        return "account-edit";
    }


}
