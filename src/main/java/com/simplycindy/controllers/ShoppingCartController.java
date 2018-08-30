package com.simplycindy.controllers;

import com.simplycindy.models.OrderItem;
import com.simplycindy.models.Product;
import com.simplycindy.models.data.OrderItemDao;
import com.simplycindy.models.data.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("cart")
public class ShoppingCartController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @RequestMapping(value = "singleProduct", method = RequestMethod.POST)
    public String buyProduct(@ModelAttribute @Valid OrderItem newOrderItem,
                             @RequestParam int productId, Model model) {

        Product aProduct = productDao.findOne(productId);

        model.addAttribute("title", aProduct.getName());
        model.addAttribute("product", aProduct);

        newOrderItem.setProductId(productId);
        newOrderItem.setPrice(aProduct.getPrice());

        orderItemDao.save(newOrderItem);

        return "product/singleProductDisplay";
    }

    @RequestMapping("")
    public String view(Model model) {

        return "cart/view";
    }
}
