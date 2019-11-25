package com.nowak.SpringBoot.Thymeleaf.controllers;

import com.nowak.SpringBoot.Thymeleaf.entities.Account;
import com.nowak.SpringBoot.Thymeleaf.models.AccountModel;
import com.nowak.SpringBoot.Thymeleaf.models.EmailModel;
import com.nowak.SpringBoot.Thymeleaf.service.AppService;
import com.nowak.SpringBoot.Thymeleaf.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private AppService appService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login-page";
    }


    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public String showResetPasswordPage(Model model) {
        model.addAttribute("emailModel", new EmailModel());
        return "reset-password";
    }

    @RequestMapping(value = "/proceedReset", method = RequestMethod.POST)
    public String proceedReset(@Valid @ModelAttribute("emailModel") EmailModel emailModel,BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("email_error", bindingResult.getFieldError().getDefaultMessage());
            return "reset-password";
        }
        Account existingAccount = appService.findByEmail(emailModel.getEmail());
        if (existingAccount == null) {
            model.addAttribute("email_error", "There are no account with that e-mail");
            return "reset-password";
        } else if (!bindingResult.hasErrors()) {
            existingAccount.setConfirmToken(UUID.randomUUID().toString());
            appService.save(existingAccount);
            String accUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            SimpleMailMessage resetMessage = new SimpleMailMessage();
            resetMessage.setTo(existingAccount.getEmail());
            resetMessage.setSubject("DiZZY - reset password ");
            resetMessage.setText("Hi " + existingAccount.getName() + "!\n\nHere is your link to reset your password. \n" + accUrl + "/confirm?token=" + existingAccount.getConfirmToken()+"\n\nIf you did not take that action - ignore that message. \nDiZZy TEAM.");
            resetMessage.setFrom("noreply@DiZZY.com");

            emailService.sendEmail(resetMessage);
            model.addAttribute("email_success","Link to reset password send to "+existingAccount.getEmail());
        }
        return "reset-password";
    }
}
