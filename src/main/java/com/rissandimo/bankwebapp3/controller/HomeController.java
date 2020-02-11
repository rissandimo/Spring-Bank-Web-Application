package com.rissandimo.bankwebapp3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
    @GetMapping("/")
    public String showUserPage()
    {
        return "redirect:/users/showUser";
    }
    @GetMapping("/login")
    public String getLoginPage()
    {
        return "login";
    }

    @GetMapping("/accessDenied")
    public String getAccessDeniedPage()
    {
        return "access-denied";
    }
}
