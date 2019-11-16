package com.nowak.SpringBoot.Thymeleaf.service;

import com.nowak.SpringBoot.Thymeleaf.dao.*;
import com.nowak.SpringBoot.Thymeleaf.entities.*;
import com.nowak.SpringBoot.Thymeleaf.models.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppServiceClass implements AppService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private AuthorityRepo authorityRepo;

    @Autowired
    private FileRepo fileRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ReportedRepo reportedRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public Account findByName(String userName) {
        return accountRepo.findByName(userName);
    }

    @Override
    @Transactional
    public void save(Account account) {
        System.out.println("saving account...");
        accountRepo.save(account);
    }

    @Override
    public Account convertToAccount(AccountModel accountModel) {
        Account account = new Account();
        account.setName(accountModel.getName());
        account.setEmail(accountModel.getEmail());
        account.setAuthorities(Arrays.asList(authorityRepo.findByAuthorityName("ROLE_USER")));
        return account;
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepo.findByEmail(email);
    }

    @Override
    public Account findByConfirmToken(String confirmToken) {
        return accountRepo.findByConfirmToken(confirmToken);
    }

    @Override
    public Account getLoggedAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = findByName(authentication.getName());
        return account;
    }


    @Override
    @Transactional
    public File findByTitle(String title) {
        return fileRepo.findByTitle(title);
    }

    @Override
    public void saveFile(File file) {
        fileRepo.save(file);
    }

    @Override
    public void deleteFileByTitle(String title) {
        File file = fileRepo.findByTitle(title);
        fileRepo.deleteFileById(file.getId());
    }

    @Override
    public void deleteFileById(int id) {
        fileRepo.deleteFileById(id);
    }


    @Override
    public List<File> findAll() {
        return fileRepo.findAll();
    }

    @Override
    public File findById(int id) {
        Optional<File> f= fileRepo.findById(id);
        File file=null;
        if(f.isPresent()){
            file=f.get();
        }
        return file;
    }

    @Override
    public void save(Comments comment) {
        comment.setDate(new Date());
        commentRepo.save(comment);
    }

    @Override
    public List<Comments> findAllByFileID(int id) {
        return commentRepo.findAllByFileID(id);
    }

    @Override
    public List<Comments> findAllComments() {
        return commentRepo.findAll();
    }

    @Override
    public List<Comments> commentsFromFile(List<File> files) {
        // map (fileId, commentId);
        Map<File,Comments> map = new HashMap<File, Comments>();
        return null;
    }

    @Override
    public void save(Reported reported) {
        reportedRepo.save(reported);
    }

    private Collection<SimpleGrantedAuthority> mapToAuthorities(Collection<Authority> authorities) {
        System.out.println("converting roles...");
        return authorities.stream().map(a -> new SimpleGrantedAuthority(a.getAuthority())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepo.findByName(s);
        if (account == null)
            throw new UsernameNotFoundException("Account not found");
        return new User(account.getName(), account.getPassword(), mapToAuthorities(account.getAuthorities()));
    }

}
