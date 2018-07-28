package com.simplycindy.controllers;

import com.simplycindy.models.User;
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

    @RequestMapping(value="login", method=RequestMethod.GET)
    public String displayLoginForm(Model model) {
        model.addAttribute("title", "Login");

        return "login/index";
    }

    @RequestMapping(value = "login", method=RequestMethod.POST)
    public String processLoginForm(Model model, @ModelAttribute @Valid User user, Errors errors) {

        model.addAttribute(user);
        model.addAttribute("title", "Login");

        if (errors.hasErrors()) {
            user.setPassword("");
            model.addAttribute("message", "Incorrect password");

            return "login/index";
        }

        return  "product/index";
    }
}
