package com.nowak.SpringBoot.Thymeleaf.service;

import com.nowak.SpringBoot.Thymeleaf.entities.*;
import com.nowak.SpringBoot.Thymeleaf.models.AccountModel;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface AppService extends UserDetailsService {

    Account findByName(String userName);

    void save(Account account);

    List<Account> findAllAccountsByName(String name);

    void deleteAccountById(int id);

    Account findAccountById(int id);

    Account convertToAccount(AccountModel accountModel);

    Account findByEmail(String email);

    Account findByConfirmToken(String confirmToken);

    Account getLoggedAccount();

    List<Account> findAllAccounts();

    File findByTitle(String title);

    void saveFile(File file);

    void deleteFileByTitle(String title);

    void deleteFileById(int id);

    List<File> findAll();

    File findById(int id);

    void save(Comments comment);

    void deleteCommentAndReportedComment(int id);

    List<Comments> findAllByFileID(int id);

    List<Comments> findAllComments();

    List<Comments> commentsFromFile(List<File> files);

    List<Comments> findAllCommentsByUserNick(String userNick);

    void deleteCommentById(int id);

    void save(Reported reported);

    List<Reported> findAllReported();

    Reported findReportedByFileID(int id);

    Reported findReportedById(int id);

    void deleteReportedById(int id);

    List<File> findAllByUserNick(String nick);

    void saveReportedComment(ReportedComments reportedComment);

    public void deleteFileByReportedId(int id);

    void deleteReportedCommentById(int id);

    void deleteCommentsFromFileById(int fileId);

    Comments findCommentById(int id);

    List<ReportedComments> findAllReportedComments();

    void saveCubby(Cubby cubby);

    List<Cubby> findAllByEmail(String email);

    List<Cubby> findAllByFileId(int id);

    Cubby findCubbyById(int id);

    void deleteCubby(Cubby cubby);

}
