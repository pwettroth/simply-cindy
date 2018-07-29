package com.simplycindy.controllers;

import com.simplycindy.models.User;
import com.simplycindy.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value="", method=RequestMethod.GET)
    public String displayLoginForm(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("user", new User());

        return "login/index";
    }

    @RequestMapping(value = "", method=RequestMethod.POST)
    public String processLoginForm(Model model, @ModelAttribute @Valid User logInUser, Errors errors) {

        model.addAttribute(logInUser);
        model.addAttribute("title", "Login");

        User actualUser = userDao.findByUsername(logInUser.getUsername());
        if (errors.hasErrors() || actualUser == null || !actualUser.getPassword().equals(logInUser.getPassword())) {
            model.addAttribute("message", "Incorrect username/password");
            logInUser.setPassword("");
            return "login/index";
        }

        //TODO store user in session

        return  "product/index";
    }


}
