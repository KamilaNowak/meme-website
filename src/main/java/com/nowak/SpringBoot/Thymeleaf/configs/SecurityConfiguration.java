package com.nowak.SpringBoot.Thymeleaf.configs;


import com.nowak.SpringBoot.Thymeleaf.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountAuthentication accountAuthentication;
    @Autowired
    private AppService appService;

    @Override
    protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/add-meme/**").hasRole("USER")
                .antMatchers("/account/**").hasAnyRole("USER", "MODERATOR")
                .antMatchers("/cubby/**").hasAnyRole("USER", "MODERATOR")
                .antMatchers("/unpin/**").hasAnyRole("USER", "MODERATOR")
                .antMatchers("/like/**").hasAnyRole("USER", "MODERATOR")
                .antMatchers("/dislike/**").hasAnyRole("USER", "MODERATOR")
                .antMatchers("/comment/**").hasAnyRole("USER", "MODERATOR")
                .antMatchers("/report/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("MODERATOR")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/proceedLogin")
                //  .defaultSuccessUrl("/")   //auth error with it
                .successHandler(accountAuthentication)
                .permitAll()
                .and()
                .logout()
                .permitAll()
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/error");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(appService);
        return provider;
    }
}
