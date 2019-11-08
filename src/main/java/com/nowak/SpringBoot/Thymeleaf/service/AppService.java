package com.nowak.SpringBoot.Thymeleaf.service;

import com.nowak.SpringBoot.Thymeleaf.entities.Account;
import com.nowak.SpringBoot.Thymeleaf.models.AccountModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AppService extends UserDetailsService {

    Account findByName(String userName);
    void save(Account account);
    Account convertToAccount(AccountModel accountModel);
    Account findByEmail(String email);
    Account findByConfirmToken(String confirmToken);
}
