package com.nowak.SpringBoot.Thymeleaf.dao;

import com.nowak.SpringBoot.Thymeleaf.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepo extends JpaRepository<Account,Integer>{

    @Query("select a from Account a where a.name= ?1")
    Account findByName(String name);


    Account findByEmail(String email);
    Account findByConfirmToken(String confirmToken);

}
