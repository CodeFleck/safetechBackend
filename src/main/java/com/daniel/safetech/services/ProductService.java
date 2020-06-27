package com.daniel.safetech.services;

import com.daniel.safetech.enitities.Product;

import java.util.Optional;

public interface ProductService {

    Iterable<Product> listAllProducts();

    Optional<Product> getProductById(Integer id);

    Product saveProduct(Product product);

    void deleteProductById(Integer id);

    void deleteProduct(Product product);

}
