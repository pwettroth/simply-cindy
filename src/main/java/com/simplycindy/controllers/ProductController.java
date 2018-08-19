package com.simplycindy.controllers;

import com.simplycindy.models.Category;
import com.simplycindy.models.Product;
import com.simplycindy.models.data.CategoryDao;
import com.simplycindy.models.data.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    //Save the uploaded file to this folder
    private static final String UPLOADED_FOLDER = "D:\\IdeaProjects\\simply-cindy\\src\\main\\resources\\static\\images\\";


    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("products", productDao.findAll());
        model.addAttribute("title", "Simply Cindy");
        return "product/adminIndex";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddProductForm(Model model) {
        model.addAttribute("title", "Add Product");
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryDao.findAll());
        return "product/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String processAddProductForm(@ModelAttribute @Valid Product newProduct,
                                        @RequestParam("categoryId") int categoryId,
                                        @RequestParam("file") MultipartFile file,
                                        Errors errors,
                                        RedirectAttributes redirectAttributes,
                                        Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Simply Cindy");
            return "product/add";
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
            newProduct.setImage("/images/" + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Category cat =categoryDao.findOne(categoryId);
        newProduct.setCategory(cat);
        productDao.save(newProduct);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveProductForm(Model model) {
        model.addAttribute("products", productDao.findAll());
        model.addAttribute("title", "Remove Product");
        return "product/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveProductForm(@RequestParam int[] productIds) {
        for (int productId : productIds) {
            productDao.delete(productId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "display")
    public String displayProducts(Model model) {
        model.addAttribute("title", "Products");
        model.addAttribute("products", productDao.findAll());
        return "product/display";
    }

    @RequestMapping(value = "aProduct", method = RequestMethod.GET)
    public String displaySingleProduct(@RequestParam int productId,
                                       Model model) {

        Product aProduct = productDao.findOne(productId);
        model.addAttribute("title", aProduct.getName());
        model.addAttribute("name", aProduct.getName());
        model.addAttribute("description", aProduct.getDescription());
        model.addAttribute("image", aProduct.getImage());
        model.addAttribute("price", aProduct.getPrice());

        return "product/singleProductDisplay";
    }

    @RequestMapping(value = "display", params = "categoryId", method = RequestMethod.GET)
    public String displayProductsByCategory (@RequestParam int categoryId, Model model) {
        List<Product> products = productDao.findAll(categoryId);

        model.addAttribute("title", categoryDao.findOne(categoryId).getName());
        model.addAttribute("products", products);

        return "product/display";
    }
}