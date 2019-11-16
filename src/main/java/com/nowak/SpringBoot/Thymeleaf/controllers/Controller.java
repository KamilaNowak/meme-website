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
        model.addAttribute("files", files);
        model.addAttribute("cmtModel", new CommentModel());
        model.addAttribute("comments", comments);
        return "main-page";
    }

    @GetMapping("/add-meme")
    public String showUploadMemePage(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("memeModel", new MemeModel());
        return "upload-meme";
    }

    @GetMapping("/account")
    public String showAccountPage(Model model) {
        model.addAttribute("account", appService.getLoggedAccount());
        return "account-page";
    }

    @GetMapping("/account/edit")
    public String editAccount(Model model) {
        model.addAttribute("account", new AccountModel());
        return "account-edit";
    }

    @RequestMapping(value = "/proceedEdit", method = RequestMethod.POST)
    public String proceedEditAccount(@Valid @ModelAttribute("account") AccountModel accountModel, BindingResult bindingResult, Model model) {
        Account existUser = appService.findByName(accountModel.getName());
        if (existUser != null) {
            model.addAttribute("edit_error", "This username is taken. Please choose another one");
            return "account-edit";
        }
        Account existEmail = appService.findByEmail(accountModel.getEmail());
        if (existEmail != null) {
            model.addAttribute("edit_error", "Account with that e-mail already exists. Please choose another one");
            return "account-edit";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("edit_error", bindingResult.getFieldError().getDefaultMessage());
            return "account-edit";
        } else {
//            if (accountModel.getName() != null) {
//                String name = accountModel.getName();
//                Account account = appService.getLoggedAccount();
//                account.setName(name);
//                appService.save(account);
//            }
//            if(accountModel.getEmail()!=null){
//                String email = accountModel.getEmail();
//                Account account=appService.getLoggedAccount();
//                account.setEmail(email);
//                appService.save(account);
//            }
//            if(accountModel.getName()!=null && accountModel.getEmail()!=null){
            String name = accountModel.getName();
            String email = accountModel.getEmail();
            Account account = appService.getLoggedAccount();
            account.setName(name);
            account.setEmail(email);
            appService.save(account);
            // }
            model.addAttribute("edit_success", "Account updated");
        }
        return "account-edit";
    }

}
