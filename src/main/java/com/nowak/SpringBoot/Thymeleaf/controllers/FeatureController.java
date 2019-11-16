package com.nowak.SpringBoot.Thymeleaf.controllers;

import com.nowak.SpringBoot.Thymeleaf.entities.Account;
import com.nowak.SpringBoot.Thymeleaf.entities.Comments;
import com.nowak.SpringBoot.Thymeleaf.entities.File;
import com.nowak.SpringBoot.Thymeleaf.entities.Reported;
import com.nowak.SpringBoot.Thymeleaf.models.CommentModel;
import com.nowak.SpringBoot.Thymeleaf.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class FeatureController {

    private AppService appService;

    @Autowired
    public FeatureController(AppService appService) {
        this.appService = appService;
    }

    @RequestMapping(value = "/like/{id}")
    public String likeFile(@PathVariable("id") int id) {
        File file = appService.findById(id);
        int l = file.getLikes();
        l++;
        file.setLikes(l);
        appService.saveFile(file);
        return "redirect:/";
    }

    @RequestMapping(value = "/dislike/{id}")
    public String dislikeFile(@PathVariable("id") int id) {
        File file = appService.findById(id);
        int d = file.getDislikes();
        d++;
        file.setDislikes(d);
        appService.saveFile(file);
        return "redirect:/";
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public String processAddingComment(@Valid @ModelAttribute("cmtModel")CommentModel commentModel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/";
        }
        Comments comment = new Comments(appService.getLoggedAccount().getName(),commentModel.getFileID(),commentModel.getComment(),new Date());
        appService.save(comment);
        return "redirect:/";
    }
    @RequestMapping(value = "/report/{id}")
    public String processReport(@PathVariable("id") int id){
        String reportingUser =appService.getLoggedAccount().getName();
        Reported reportedFile = new Reported();
        reportedFile.setFileID(id);
        reportedFile.setReportingUser(reportingUser);
        appService.save(reportedFile);
        return "redirect:/";
    }
}
