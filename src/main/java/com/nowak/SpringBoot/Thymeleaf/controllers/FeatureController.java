package com.nowak.SpringBoot.Thymeleaf.controllers;

import com.nowak.SpringBoot.Thymeleaf.entities.*;
import com.nowak.SpringBoot.Thymeleaf.models.CommentModel;
import com.nowak.SpringBoot.Thymeleaf.service.AppService;
import com.nowak.SpringBoot.Thymeleaf.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
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

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/like/{id}")
    public String likeFile(@PathVariable("id") int id) {
        File file = appService.findById(id);
        Account account = appService.getLoggedAccount();
        UserFileLikes existLike = appService.findUserFileLikeByFileId(id);
        UserFileDislikes existDislike =appService.findUserFileDislikeByFileId(id);
        if (existLike != null) {
            if ((existLike.getUserId() != account.getId())) {
                int likes = file.getLikes();
                likes++;
                file.setLikes(likes);
                appService.saveFile(file);
                UserFileLikes userFileLike = new UserFileLikes(account.getId(), file.getId());
                appService.saveUserFileLike(userFileLike);
                if(existDislike!=null) {
                    appService.deleteUserFileDislike(existDislike);
                    if(file.getDislikes()>0){
                        int dislikes = file.getDislikes();
                        dislikes--;
                        file.setDislikes(dislikes);
                        appService.saveFile(file);
                    }
                }
            } else {
                int likes = file.getLikes();
                likes--;
                file.setLikes(likes);
                appService.saveFile(file);
            }
        }
        if (existLike == null) {
            int likes = file.getLikes();
            likes++;
            file.setLikes(likes);
            appService.saveFile(file);
            UserFileLikes userFileLike = new UserFileLikes(account.getId(), file.getId());
            appService.saveUserFileLike(userFileLike);
            if(existDislike!=null ) {
                appService.deleteUserFileDislike(existDislike);
                if(file.getDislikes()>0){
                    int dislikes = file.getDislikes();
                    dislikes--;
                    file.setDislikes(dislikes);
                    appService.saveFile(file);
                }
            }
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/dislike/{id}")
    public String dislikeFile(@PathVariable("id") int id) {
        File file = appService.findById(id);
        Account account = appService.getLoggedAccount();
        UserFileDislikes existDislike = appService.findUserFileDislikeByFileId(id);
        UserFileLikes existLike = appService.findUserFileLikeByFileId(id);
        if (existDislike != null) {
            if (existDislike.getUserId() != account.getId()) {
                int dislikes = file.getDislikes();
                dislikes++;
                file.setDislikes(dislikes);
                appService.saveFile(file);
                UserFileDislikes userFileDislike = new UserFileDislikes(account.getId(), file.getId());
                appService.saveUserFileDislike(userFileDislike);
                if(existLike!=null) {
                    appService.deleteUserFileLike(existLike);
                    if(file.getLikes()>0){
                        int likes = file.getLikes();
                        likes--;
                        file.setLikes(likes);
                        appService.saveFile(file);
                    }
                }
            }
            else{
                int dislikes=file.getDislikes();
                dislikes--;
                file.setDislikes(dislikes);
                appService.saveFile(file);
                appService.deleteUserFileDislike(existDislike);
            }
        }
        if(existDislike==null){
            int dislikes = file.getDislikes();
            dislikes++;
            file.setDislikes(dislikes);
            appService.saveFile(file);
            UserFileDislikes userFileDislike = new UserFileDislikes(account.getId(), file.getId());
            appService.saveUserFileDislike(userFileDislike);
            if(existLike!=null) {
                appService.deleteUserFileLike(existLike);
                if(file.getLikes()>0){
                    int likes = file.getLikes();
                    likes--;
                    file.setLikes(likes);
                    appService.saveFile(file);
                }
            }
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public String processAddingComment(@Valid @ModelAttribute("cmtModel") CommentModel commentModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        Comments comment = new Comments(appService.getLoggedAccount().getName(), commentModel.getFileID(), commentModel.getComment(), new Date());
        appService.save(comment);
        return "redirect:/";
    }

    @RequestMapping(value = "/report/{id}")
    public String processReport(@PathVariable("id") int id) {
        String reportingUser = appService.getLoggedAccount().getName();
        Reported reportedFile = new Reported();
        reportedFile.setFileID(id);
        reportedFile.setReportingUser(reportingUser);
        appService.save(reportedFile);
        return "redirect:/";
    }

    @RequestMapping(value = "/report-comment/{id}")
    public String processReportComment(@PathVariable("id") int id) {
        int commentID = appService.findCommentById(id).getId();
        String repostingUser = appService.getLoggedAccount().getName();
        ReportedComments rc = new ReportedComments(commentID, repostingUser);
        appService.saveReportedComment(rc);
        return "redirect:/";
    }

    @RequestMapping(value = "/ban/{id}")
    public String banUser(@PathVariable("id") int id) {
        Account accountToBan = appService.findAccountById(id);

        SimpleMailMessage informMessageAboutBan = new SimpleMailMessage();
        informMessageAboutBan.setTo(accountToBan.getEmail());
        informMessageAboutBan.setSubject("DiZZy - information about banned account");
        informMessageAboutBan.setText("Dear " + accountToBan.getName() + ", " + "\nYou account has been banned permanently, because of broken DizzyⓇ meme website rules.\nYour account could not be restored.\n\nDiZZy Support.");
        informMessageAboutBan.setFrom("noreply@DiZZy.com");
        emailService.sendEmail(informMessageAboutBan);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") int id) {
        appService.deleteCommentAndReportedComment(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/cubby/{id}")
    public String saveToCubby(@PathVariable("id") int id) {
        String email = appService.getLoggedAccount().getEmail();
        Cubby cubby = new Cubby(email, id);
        appService.saveCubby(cubby);
        return "redirect:/";
    }

    @RequestMapping(value = "/unpin/{id}")
    public String unpinFromCubby(@PathVariable("id") int id) {
        Cubby cubby = appService.findCubbyById(id);
        appService.deleteCubby(cubby);
        return "redirect:/account";
    }

    @RequestMapping(value = "/account/file/delete/{id}")
    public String deleteFile(@PathVariable("id") int id) {
        appService.deleteFileById(id);
        return "redirect:/account";
    }


}
