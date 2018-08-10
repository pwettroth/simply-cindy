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
@RequestMapping("")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value="add", method=RequestMethod.GET)
    public String displayAddUserForm(Model model) {
        model.addAttribute("title", "User Signup");
        model.addAttribute(new User());
        return "user/add";
    }

    @RequestMapping(value="add", method=RequestMethod.POST)
    public String processAddUserForm(Model model, @ModelAttribute @Valid User newUser,
                                     Errors errors, String verify) {
        model.addAttribute("title", "User Signup");
        model.addAttribute(newUser);

        if (errors.hasErrors()) {
            newUser.setPassword("");
            model.addAttribute("message", "Error adding user");
            return "user/add";
        } else if(newUser.getPassword() == null || !newUser.getPassword().equals(verify)) {
            newUser.setPassword("");
            model.addAttribute("message", "Passwords do not match");
            return "user/add";
        } else {
            model.addAttribute("title", "Simply Cindy");
            userDao.save(newUser);
            return "user/index";
        }
    }
}
