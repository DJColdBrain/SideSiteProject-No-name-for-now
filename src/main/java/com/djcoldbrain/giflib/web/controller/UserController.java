package com.djcoldbrain.giflib.web.controller;

import com.djcoldbrain.giflib.model.User;
import com.djcoldbrain.giflib.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String loginForm(Model model, HttpServletRequest request){
        model.addAttribute("user", new User());
        try {
            Object flash = request.getSession().getAttribute("flash");
            model.addAttribute("flash", flash);

            request.removeAttribute("flash");
        }catch (Exception ex){

        }
        model.addAttribute("action", "/login");
        model.addAttribute("heading", "Login");
        model.addAttribute("submit", "Login");
        return "user/login";
    }

}
