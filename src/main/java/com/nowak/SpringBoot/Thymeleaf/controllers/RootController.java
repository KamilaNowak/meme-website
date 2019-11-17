package com.nowak.SpringBoot.Thymeleaf.controllers;

import com.nowak.SpringBoot.Thymeleaf.entities.Account;
import com.nowak.SpringBoot.Thymeleaf.entities.File;
import com.nowak.SpringBoot.Thymeleaf.entities.Reported;
import com.nowak.SpringBoot.Thymeleaf.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        List<Reported> reportedFiles=new ArrayList<>();
        List<Account> accounts = appService.findAllAccounts();
        List<File> files=appService.findAll();

        for(Reported i: allReportedFiles){
            int id = i.getFileID();
            String repoPath = appService.findById(id).getPath();
            String addedBy =appService.findById(id).getUserNick();
            Date date=appService.findById(id).getData();
            String fileTitle= appService.findById(id).getTitle();
            Reported r = new Reported(i.getReportingUser(),date,repoPath,addedBy,fileTitle);
            reportedFiles.add(r);
        }

        model.addAttribute("reportedFiles",reportedFiles);
        model.addAttribute("accounts",accounts);
        model.addAttribute("files",files);
        return "admin-page";
    }
}
