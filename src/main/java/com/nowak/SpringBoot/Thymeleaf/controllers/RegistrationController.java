package com.nowak.SpringBoot.Thymeleaf.controllers;

import com.nowak.SpringBoot.Thymeleaf.entities.Account;
import com.nowak.SpringBoot.Thymeleaf.models.AccountModel;
import com.nowak.SpringBoot.Thymeleaf.models.PasswordModel;
import com.nowak.SpringBoot.Thymeleaf.service.AppService;
import com.nowak.SpringBoot.Thymeleaf.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Controller
public class RegistrationController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AppService appService;
    private EmailService emailService;

    @Autowired
    public RegistrationController(BCryptPasswordEncoder bCryptPasswordEncoder, AppService appService, EmailService emailService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.appService = appService;
        this.emailService = emailService;
    }


    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("accountModel", new AccountModel());
        return "register-page";
    }

    @RequestMapping(value = "/proceedRegister", method = RequestMethod.POST)
    public String proceedRegister(@Valid @ModelAttribute("accountModel") AccountModel accountModel, BindingResult bindingResult, Model model, HttpServletRequest servletRequest) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("validation_error", bindingResult.getFieldError().getDefaultMessage());
            return "register-page";
        }
        Account existingAccount = appService.findByName(accountModel.getName());
        if (existingAccount != null) {
            model.addAttribute("validation_error", "User already exists!");
            return "register-page";
        }
        Account existingEmail = appService.findByEmail(accountModel.getEmail());
        if (existingEmail != null) {
            model.addAttribute("validation_error", "Account with email " + accountModel.getEmail() + " elready exists ");
            return "register-page";
        }
        if (!bindingResult.hasErrors()) {
            Account account = appService.convertToAccount(accountModel);
            account.setPhoto("https://dizzys3.s3.eu-central-1.amazonaws.com/unknow.png");
            account.setConfirmToken(UUID.randomUUID().toString());
            account.setEnabled(true);
            appService.save(account);

            String accountUrl = servletRequest.getScheme() + "://" + servletRequest.getServerName() + ":" + servletRequest.getServerPort(); //manually added port -check later TODO
            SimpleMailMessage confirmationEmail = new SimpleMailMessage();
            confirmationEmail.setTo(account.getEmail());
            confirmationEmail.setSubject("DiZZY - Account Registration Confirmation");
            confirmationEmail.setText("To fully enable you account please hit the link below and set your password.\n" + accountUrl + "/confirm?token=" + account.getConfirmToken()+"\nDiZZy TEAM.");
            confirmationEmail.setFrom("noreply@DiZZy.com");

            emailService.sendEmail(confirmationEmail);
            model.addAttribute("validation_success", "Confirmation email sent to " + account.getEmail());
        }
        System.out.println(accountModel.getName() + "  " + accountModel.getEmail());
        return "register-page";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String showConfirmPasswordPage(@RequestParam("token") String token, Model model) {
        Account accountToConfirm = appService.findByConfirmToken(token);
        if (accountToConfirm == null) {
            return "error-page";
        } else {
            model.addAttribute("accountToConfirm", new PasswordModel());
            model.addAttribute("confirmToken", accountToConfirm.getConfirmToken());
        }
        return "password-confirm-page";
    }

    @PostMapping(value = {"/confirm", "/reset"})
    public String proceedConfirmationPassword(@Valid @ModelAttribute("accountToConfirm") PasswordModel account, BindingResult bindingResult, Model model, @RequestParam("token") String token) {
        Account foundAccount = appService.findByConfirmToken(token);
        if (bindingResult.hasErrors()) {
            model.addAttribute("confirm_error", "Fill correctly your new password!");
            return "password-confirm-page";
        }
        model.addAttribute("confirm_success", "Password set successully! Now you can log in");
        foundAccount.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        appService.save(foundAccount);
        return "password-confirm-page";
    }
}
