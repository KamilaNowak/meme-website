package com.nowak.SpringBoot.Thymeleaf.configs;

import com.nowak.SpringBoot.Thymeleaf.entities.Account;
import com.nowak.SpringBoot.Thymeleaf.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Component
public class AccountAuthentication implements AuthenticationSuccessHandler {

    @Autowired
    private AppService appService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        System.out.println("In auth......");
         String name = authentication.getName();
         Account account = appService.findByName(name);
             HttpSession httpSession = httpServletRequest.getSession();
             httpSession.setAttribute("account", account);
             httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/");

    }
}
