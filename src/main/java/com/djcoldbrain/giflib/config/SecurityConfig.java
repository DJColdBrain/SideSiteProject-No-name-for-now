package com.djcoldbrain.giflib.config;

import com.djcoldbrain.giflib.service.UserService;
import com.djcoldbrain.giflib.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .and()
            .authorizeRequests()
                .anyRequest().hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailHandler())
                .and()
            .logout()
            .permitAll()
            .logoutSuccessUrl("/login");

    }


    private AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, auth) -> response.sendRedirect("/");
    }

    public AuthenticationFailureHandler loginFailHandler (){
        return (request, response, authentication) -> {
            request.getSession().setAttribute("flash", new FlashMessage("Incorect username or password", FlashMessage.Status.FAILURE));
            response.sendRedirect("/login");
        };
    }


}
