package com.nowak.SpringBoot.Thymeleaf.service;

import com.nowak.SpringBoot.Thymeleaf.dao.AccountRepo;
import com.nowak.SpringBoot.Thymeleaf.dao.AuthorityRepo;
import com.nowak.SpringBoot.Thymeleaf.dao.FileRepo;
import com.nowak.SpringBoot.Thymeleaf.entities.Account;
import com.nowak.SpringBoot.Thymeleaf.entities.Authority;
import com.nowak.SpringBoot.Thymeleaf.entities.File;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
    private BCryptPasswordEncoder encoder;


    @Override
    @Transactional
    public Account findByName(String userName) {
        // return accountRepo.findByUserName(userName);
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
    public File findByUserID(int id) {
        return fileRepo.findByUserID(id);
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
    public List<File> findAllByUserID(int id) {
        Account account= getLoggedAccount();
        int accID = account.getId();
        List<File> files = fileRepo.findAllByUserID(accID);
        return files;
    }

    @Override
    public List<File> findAll() {
        return fileRepo.findAll();
    }

    private Collection<SimpleGrantedAuthority> mapToAuthorities(Collection<Authority> authorities) {
        System.out.println("converting roles...");
        return authorities.stream().map(a -> new SimpleGrantedAuthority(a.getAuthority())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("Loading user....");
        Account account = accountRepo.findByName(s);
        if (account == null)
            throw new UsernameNotFoundException("Account not found");
        return new User(account.getName(), account.getPassword(), mapToAuthorities(account.getAuthorities()));
    }

}
