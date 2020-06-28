package com.daniel.safetech.controllers;

import com.daniel.safetech.enitities.Product;
import com.daniel.safetech.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    private byte[] bytes;

    @GetMapping("/")
    public List<Product> list(Model model) {
        return productService.listAllProducts();
    }

    @PostMapping("/upload")
    public void uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        this.bytes = file.getBytes();
    }

    @PostMapping("/add")
    public void createBook(@RequestBody Product book) throws IOException {
        book.setPicture(this.bytes);
        productService.saveProduct(book);
        this.bytes = null;
    }

    @DeleteMapping(path = { "/{id}" })
    public Product deleteProduct(@PathVariable("id") Integer id) throws Exception {
        Product product = productService.getProductById(id).orElseThrow(() -> new Exception("Product not found - " + id));;
        productService.deleteProduct(product);
        return product;
    }
}
