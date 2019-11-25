package com.nowak.SpringBoot.Thymeleaf.controllers;

import com.nowak.SpringBoot.Thymeleaf.entities.*;
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
import java.util.stream.Collectors;


@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private AppService appService;

    @GetMapping("/")
    public String showMain(Model model) {
        List<File> files = appService.findAll();
        List<UserFileLikes> AllLikes= appService.findAllLikes();
        List<UserFileLikes> likes = new ArrayList<>();
        List<Comments> comments = appService.findAllComments();
        for (Comments i : comments) {
            String nick = i.getUserNick();
            String photo = appService.findByName(nick).getPhoto();
            i.setPhoto(photo);
        }
        for (UserFileLikes u: AllLikes) {
            String userName = appService.findAccountById(u.getUserId()).getName();
            UserFileLikes ufl = new UserFileLikes(u.getUserId(),u.getFileId(),userName);
            ufl.setId(u.getId());
            likes.add(ufl);
        }
        model.addAttribute("files", files);
        model.addAttribute("cmtModel", new CommentModel());
        model.addAttribute("comments", comments);
        model.addAttribute("likes",likes.stream().map(like -> like.getUserNick()).collect(Collectors.joining("\n")));
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
        List<Cubby> userCubbiesRare = appService.findAllByEmail(appService.getLoggedAccount().getEmail());
        List<Cubby> userCubbies = new ArrayList<>();
        for (Cubby c : userCubbiesRare) {
            Cubby cubby = new Cubby();
            cubby.setId(c.getId());
            cubby.setEmail(c.getEmail());
            cubby.setFileID(c.getFileID());
            //    cubby.setPath(appService.findById(c.getFileID()).getPath());
            File file = appService.findById(c.getFileID());
            String path = file.getPath();
            System.out.println("=====>>>>" + path);
            cubby.setPath(path);
            cubby.setUserNick(appService.findById(c.getFileID()).getUserNick());
            System.out.println(cubby.getId() + " " + cubby.getFileID());
            userCubbies.add(cubby);
        }
        model.addAttribute("account", appService.getLoggedAccount());
        model.addAttribute("userFiles", userFiles);
        model.addAttribute("userCubbies", userCubbies);
        return "account-page";
    }

    @GetMapping("/account/edit")
    public String editAccount(Model model) {
        Account account = appService.getLoggedAccount();
        AccountModel accountModel = new AccountModel();
        accountModel.setName(account.getName());
        accountModel.setEmail(account.getEmail());
        model.addAttribute("account", accountModel);
        return "account-edit";
    }


}
