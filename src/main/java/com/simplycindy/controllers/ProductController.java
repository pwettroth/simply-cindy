package com.simplycindy.controllers;

import com.simplycindy.models.Category;
import com.simplycindy.models.OrderItem;
import com.simplycindy.models.Product;
import com.simplycindy.models.data.CategoryDao;
import com.simplycindy.models.data.OrderItemDao;
import com.simplycindy.models.data.ProductDao;
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
import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private OrderItemDao orderItemDao;

    //Save the uploaded file to this folder
    private static final String UPLOADED_FOLDER = "D:\\IdeaProjects\\simply-cindy\\src\\main\\resources\\static\\images\\";


    @RequestMapping(value = "admin")
    public String adminIndex(Model model) {

        model.addAttribute("products", productDao.findAll());
        model.addAttribute("title", "Simply Cindy");
        return "admin/viewProducts";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddProductForm(Model model) {
        model.addAttribute("title", "Add Product");
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryDao.findAll());
        return "admin/addProduct";
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
            return "admin/addProduct";
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

        Category cat = categoryDao.findOne(categoryId);
        newProduct.setCategory(cat);
        productDao.save(newProduct);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveProductForm(Model model) {
        model.addAttribute("products", productDao.findAll());
        model.addAttribute("title", "Remove Product");
        return "admin/removeProduct";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveProductForm(@RequestParam int[] productIds) {
        for (int productId : productIds) {
            productDao.delete(productId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "edit/{productId}")
    public String editProduct(Model model, @PathVariable int productId) {
        Product p = productDao.findOne(productId);
        model.addAttribute("product", p);
        model.addAttribute("categories", categoryDao.findAll());

        return "admin/editProduct";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String processEditProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, Integer productId) {
        Product actualProduct = productDao.findOne(productId);
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            actualProduct.setImage("/images/" + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        actualProduct.setCategory(product.getCategory());
        actualProduct.setDescription(product.getDescription());
        actualProduct.setName(product.getName());
        actualProduct.setPrice(product.getPrice());
        productDao.save(actualProduct);

        return "redirect:";
    }

    @RequestMapping(value = "display")
    public String displayProducts(Model model) {
        model.addAttribute("title", "Products");
        model.addAttribute("products", productDao.findAll());
        model.addAttribute("categories", categoryDao.findAll());
        return "product/display";
    }

    @RequestMapping(value = "display", params = "categoryId", method = RequestMethod.GET)
    public String displayProductsByCategory (@RequestParam int categoryId, Model model) {
        List<Product> products = productDao.findAll(categoryId);

        model.addAttribute("title", categoryDao.findOne(categoryId).getName());
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryDao.findAll());

        return "product/display";
    }

    @RequestMapping(value = "aProduct", method = RequestMethod.GET)
    public String displaySingleProduct(@RequestParam int productId,
                                       Model model) {

        Product aProduct = productDao.findOne(productId);

        String name;
        if(aProduct == null) {
            name = null;
        } else {
            name = aProduct.getName();
        }
        model.addAttribute("title", name);
        model.addAttribute("product", aProduct);

        model.addAttribute("orderItem", new OrderItem());

        return "product/singleProductDisplay";
    }
}