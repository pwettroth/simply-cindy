package com.simplycindy.controllers;

import com.simplycindy.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class HomeController {

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = "")
    public String homepage(Model model) {
        model.addAttribute("title", "Simply Cindy");
        model.addAttribute("categories", categoryDao.findAll());

        return "home/index";
    }
}
