package com.daniel.safetech.repositories;

import com.daniel.safetech.enitities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
