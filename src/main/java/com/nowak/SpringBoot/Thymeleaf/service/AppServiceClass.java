package com.nowak.SpringBoot.Thymeleaf.service;

import com.nowak.SpringBoot.Thymeleaf.dao.AccountRepo;
import com.nowak.SpringBoot.Thymeleaf.dao.AuthorityRepo;
import com.nowak.SpringBoot.Thymeleaf.entities.Account;
import com.nowak.SpringBoot.Thymeleaf.entities.Authority;
import com.nowak.SpringBoot.Thymeleaf.models.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private BCryptPasswordEncoder encoder;

//    @Override
//    @Transactional
//    public List<Account> findAll() {
//        return accountRepo.findAll();
//    }

//    @Override
//    @Transactional
//    public Account findById(int id) {
//        Optional<Account> optionalAccount = accountRepo.findById(id);
//        Account account =null;
//        if(optionalAccount.isPresent()){
//            account=optionalAccount.get();
//        }
//        return account;
//    }

    @Override
    @Transactional
    public Account findByName(String userName) {
        // return accountRepo.findByUserName(userName);
        return accountRepo.findByName(userName);
    }

    //    @Override
//    @Transactional
//    public void save(Account account) {
//        System.out.println("saving account...");
//        accountRepo.save(account);
//    }
    @Override
    @Transactional
    public void save(Account account) {
        System.out.println("saving account...");
        accountRepo.save(account);
    }
//}
//
//    @Override
//    @Transactional
//    public void deleteById(int id) {
//        accountRepo.deleteById(id);
//    }

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
