package com.simplycindy.controllers;

import com.simplycindy.models.Category;
import com.simplycindy.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    private static final String UPLOADED_FOLDER = "D:\\IdeaProjects\\simply-cindy\\src\\main\\resources\\static\\images\\";

    @RequestMapping(value = "")
    public String index (Model model) {

        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", "Categories");

        return "category/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCategory(Model model) {
        model.addAttribute("title", "Add Category");
        model.addAttribute(new Category());

        return "category/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCategory(Model model, @ModelAttribute @Valid Category newCategory,
                                     @RequestParam("file") MultipartFile file,
                                     RedirectAttributes redirectAttributes, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Category");
            return "category/add";
        }

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            newCategory.setImage("/images/" + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        categoryDao.save(newCategory);

        return "redirect:../product/add";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveProductForm(Model model) {
        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", "Remove Category");
        return "category/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveProductForm(@RequestParam int[] categoryIds) {
        for (int categoryId : categoryIds) {
            categoryDao.delete(categoryId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "edit/{categoryId}")
    public String editCategory(Model model, @PathVariable int categoryId) {
        Category c = categoryDao.findOne(categoryId);
        model.addAttribute("category", c);

        return "category/edit";
    }

    @RequestMapping(value="edit", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String processEditCategory(int categoryId, String name, @RequestParam("file") MultipartFile file) {
        Category c = categoryDao.findOne(categoryId);

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            c.setImage("/images/" + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        c.setName(name);
        categoryDao.save(c);

        return "redirect:";
    }

    @RequestMapping(value="display", method=RequestMethod.GET)
    public String displayIndividualCategory(Model model) {
        model.addAttribute("title", "Category");

        return "displayIndividualCategory";
    }

    @RequestMapping(value = "aCategory", method = RequestMethod.GET)
    public String displaySingleCategory(@RequestParam int categoryId,
                                       Model model) {
        Category aCategory = categoryDao.findOne(categoryId);

        return "product/singleProductDisplay";
    }

}
