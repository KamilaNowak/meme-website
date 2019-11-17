package com.nowak.SpringBoot.Thymeleaf.service;

import com.nowak.SpringBoot.Thymeleaf.entities.*;
import com.nowak.SpringBoot.Thymeleaf.models.AccountModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface AppService extends UserDetailsService {

    Account findByName(String userName);

    void save(Account account);

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

    List<Comments> findAllByFileID(int id);

    List<Comments> findAllComments();

    List<Comments> commentsFromFile(List<File> files);

    void save(Reported reported);

    List<Reported> findAllReported();

    List<File> findAllByUserNick(String nick);

    void saveReportedComment(ReportedComments reportedComment);

    Comments findCommentById(int id);


}
