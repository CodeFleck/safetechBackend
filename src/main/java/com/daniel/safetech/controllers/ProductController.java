package com.daniel.safetech.controllers;

import com.daniel.safetech.enitities.Product;
import com.daniel.safetech.enitities.User;
import com.daniel.safetech.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Product> list(Model model) {
        return productService.listAllProducts();
    }

    @RequestMapping("product/{id}")
    public String showProduct(@PathVariable Integer id, Model model) {
        productService.getProductById(id).ifPresent(product -> model.addAttribute("product", product));
        return "productshow";
    }

    @RequestMapping("product/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        productService.getProductById(id).ifPresent(product -> model.addAttribute("product", product));
        return "productform";
    }

    @RequestMapping("product/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "productform";
    }

    @PostMapping(value = "product")
    public String saveProduct(Product product) {
        productService.saveProduct(product);
        return "redirect:/product/" + product.getId();
    }

    @DeleteMapping("product/delete/{id}")
    public String delete(@PathVariable Integer id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }

}
