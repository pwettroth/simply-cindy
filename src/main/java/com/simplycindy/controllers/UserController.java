package com.simplycindy.controllers;

import com.simplycindy.models.Login;
import com.simplycindy.models.User;
import com.simplycindy.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    public static final String userSessionKey = "email";

    @RequestMapping(value="register", method=RequestMethod.GET)
    public String displayAddUserForm(Model model) {
        model.addAttribute("title", "User Signup");
        model.addAttribute(new User());
        return "user/register";
    }

    @RequestMapping(value="register", method=RequestMethod.POST)
    public String processAddUserForm(Model model, @ModelAttribute @Valid User newUser,
                                     Errors errors, String verify, HttpServletRequest request) {
        model.addAttribute("title", "User Signup");
        model.addAttribute(newUser);

        if (errors.hasErrors()) {
            newUser.setPassword("");
            model.addAttribute("message", "Error adding user");
            return "user/register";
        }

        User existingUser = userDao.findByEmail(newUser.getEmail());

        if (existingUser != null) {
            errors.rejectValue("email", "email.alreadyexists", "A user with that email already exists");
            return "user/register";
        } else if(newUser.getPassword() == null || !newUser.getPassword().equals(verify)) {
            newUser.setPassword("");
            model.addAttribute("message", "Passwords do not match");
            return "user/register";
        } else {
            model.addAttribute("title", "Simply Cindy");
            userDao.save(newUser);
            setUserInSession(request.getSession(), newUser);

            return "redirect:/home";
        }
    }

    @RequestMapping(value="login", method=RequestMethod.GET)
    public String displayLoginForm(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("login", new Login());

        return "user/login";
    }

    @RequestMapping(value = "login", method=RequestMethod.POST)
    public String processLoginForm(Model model, @ModelAttribute @Valid Login logInUser,
                                   Errors errors, HttpServletRequest request) {

        model.addAttribute("login", logInUser);
        model.addAttribute("title", "Login");

        User actualUser = userDao.findByEmail(logInUser.getEmail());
        if (errors.hasErrors()) {
            model.addAttribute("message", errors.getAllErrors());
            logInUser.setPassword("");
            return "user/login";
        } else if(actualUser == null || !actualUser.getPassword().equals(logInUser.getPassword())) {
            model.addAttribute("message", "Incorrect username/password");
            logInUser.setPassword("");
            return "user/login";
        }

        setUserInSession(request.getSession(), actualUser);
        return  "redirect:home";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/login";
    }

    protected User getUserFromSession(HttpSession session) {
        String email = (String) session.getAttribute(userSessionKey);

        User user = null;
        if(email != null) {
            user = userDao.findByEmail(email);
        }
        return user;
    }

    protected void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getEmail());
    }

    @ModelAttribute("user")
    public User getUserForModel(HttpServletRequest request) {
        return getUserFromSession(request.getSession());
    }
}
