package com.rissandimo.bankwebapp3.controller;

import com.rissandimo.bankwebapp3.dao.User;
import com.rissandimo.bankwebapp3.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController
{
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/showUser")
    public String showUser(Model model)
    {
        logger.info("showUser()");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        model.addAttribute("user", userRepository.findUserByUsername(username));
        return "users/logged-user";
    }

    @GetMapping("/createNewUser")
    public String createNewUser(Model model)
    {
        model.addAttribute("newUser", new User());
        return "users/new-user";
    }

    @PostMapping("/processNewUser")
    public String processNewUser(@ModelAttribute("newUser") @Valid User user, Model model)
    {
        // check if user already exists
        User userExists = userRepository.findUserByUsername(user.getUsername());

        if(userExists == null) // new user
        {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(1);
            user.setRoles("USER");
            userRepository.save(user);
            return "users/new-account-confirmation";
        }
        else // user already exists
        {
            model.addAttribute("userName", user.getFullName());
            return "users/account-exists";
        }



    }

}
