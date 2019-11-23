package com.nowak.SpringBoot.Thymeleaf.controllers;

import com.nowak.SpringBoot.Thymeleaf.entities.*;
import com.nowak.SpringBoot.Thymeleaf.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        List<Reported> allReportedFiles = appService.findAllReported();
        List<Reported> reportedFiles = new ArrayList<>();
        List<ReportedComments> allReportedComments = appService.findAllReportedComments();
        for (Reported r: allReportedFiles) {
            System.out.println("aaaaaaa "+r.getId()+" "+r.getFileID());
        }
        List<ReportedComments> reported_comments = new ArrayList<>();
        List<Account> accounts = appService.findAllAccounts();
        List<File> files = appService.findAll();

            for (Reported i : allReportedFiles) {
                int id = i.getFileID();
                String repoPath = appService.findById(id).getPath();
                String addedBy = appService.findById(id).getUserNick();
                Date date = appService.findById(id).getData();
                String fileTitle = appService.findById(id).getTitle();
                Reported r = new Reported(i.getId(),i.getFileID(),i.getReportingUser(), date, repoPath, addedBy, fileTitle);
                System.out.println(r.getId());
                reportedFiles.add(r);
            }

        for (ReportedComments r : allReportedComments) {
            int id = r.getId(); //comment ID
            String user = appService.findCommentById(id).getUserNick();
            Comments comment = appService.findCommentById(id);
            int fileId = comment.getFileID();
            String text = comment.getComment();
            String photoPath = appService.findByName(user).getPhoto();
            Date date = comment.getDate();
            ReportedComments rc = new ReportedComments(r.getId(),r.getCommentID(),r.getReportingUser(), user, fileId, text, photoPath, date);
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
}
