package com.simplycindy.simplycindy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping("product")
public class ProductController {

    static ArrayList<String> products = new ArrayList<>();


    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("products", products);
        model.addAttribute("title", "Simply Cindy");
        return "product/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddProductForm(Model model) {
        model.addAttribute("title", "Add Product");
        return "product/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddProductForm(@RequestParam String productName) {
        products.add(productName);
        return "redirect:";
    }
}
