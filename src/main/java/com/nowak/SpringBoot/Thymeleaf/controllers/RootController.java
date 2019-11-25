package com.nowak.SpringBoot.Thymeleaf.controllers;

import com.nowak.SpringBoot.Thymeleaf.entities.*;
import com.nowak.SpringBoot.Thymeleaf.service.AppService;
import com.nowak.SpringBoot.Thymeleaf.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class RootController {

    @Autowired
    private AppService appService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        List<Reported> allReportedFiles = appService.findAllReported();
        List<Reported> reportedFiles = new ArrayList<>();
        List<ReportedComments> allReportedComments = appService.findAllReportedComments();
        List<ReportedComments> reported_comments = new ArrayList<>();
        List<Account> accounts = appService.findAllAccounts();
        List<File> files = appService.findAll();

            for (Reported i : allReportedFiles) {
                int id = i.getFileID();
                String repoPath = appService.findById(id).getPath();
                String addedBy = appService.findById(id).getUserNick();
                int userId =appService.findByName(addedBy).getId();
                Date date = appService.findById(id).getData();
                String fileTitle = appService.findById(id).getTitle();
                Reported r = new Reported(i.getId(),userId,i.getFileID(),i.getReportingUser(), date, repoPath, addedBy, fileTitle);
                System.out.println(r.getId());
                reportedFiles.add(r);
            }

        for (ReportedComments r : allReportedComments) {
            int id = r.getId(); //comment ID
            int commentId=r.getCommentID();
           // String user = appService.findReportedCommentById(id).getUserNick();
            String user= appService.findCommentById(commentId).getUserNick();
            int userId = appService.findByName(user).getId();
            Comments comment = appService.findCommentById(commentId);
            int fileId = comment.getFileID();
            String text = comment.getComment();
            System.out.println(id+" "+commentId+ " "+ user+" "+ comment);
            String photoPath = appService.findByName(user).getPhoto();
            Date date = comment.getDate();
            ReportedComments rc = new ReportedComments(r.getId(),userId,r.getCommentID(),r.getReportingUser(), user, fileId, text, photoPath, date);
            reported_comments.add(rc);
        }

        model.addAttribute("reportedFiles", reportedFiles);
        model.addAttribute("accounts", accounts);
        model.addAttribute("files", files);
        model.addAttribute("reportedCommentsList", reported_comments);
        return "admin-page";
    }

    @RequestMapping(value = "/admin/delete/{id}")
    public String deleteFile(@PathVariable("id") int id){
        appService.deleteFileByReportedId(id);
       return "redirect:/admin";
    }
    @RequestMapping(value = "/admin/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") int id) {
        appService.deleteCommentAndReportedComment(id);
        return "redirect:/admin";
    }
    @RequestMapping(value = "/admin/ban/{id}")
    public String banUser(@PathVariable("id") int id) {
        Account accountToBan = appService.findAccountById(id);
        accountToBan.setEnabled(false);
        SimpleMailMessage informMessageAboutBan = new SimpleMailMessage();
        informMessageAboutBan.setTo(accountToBan.getEmail());
        informMessageAboutBan.setSubject("DiZZy - information about banned account");
        informMessageAboutBan.setText("Dear " + accountToBan.getName() + ", " + "\nYou account has been banned permanently, because of broken DizzyⓇ meme website rules.\nYour account could not be restored.\n\nDiZZy Support.");
        informMessageAboutBan.setFrom("noreply@DiZZy.com");
        emailService.sendEmail(informMessageAboutBan);
        appService.save(accountToBan);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/mute/{id}/{time}")
    public String muteUser(@PathVariable("id") int userId, @PathVariable("time") int time){
        Account accountToMute = appService.findAccountById(userId);
        Date today = new Date();
        Date muteDate = new Date(today.getTime() + time*(1000 * 60 * 60));
        System.out.println("Mute Date" + muteDate);
        accountToMute.setMute(muteDate);
        accountToMute.setEnabled(false);
        appService.save(accountToMute);
        return "redirect:/admin";
    }
}
