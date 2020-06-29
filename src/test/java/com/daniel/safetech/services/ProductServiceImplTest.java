package com.daniel.safetech.services;

import com.daniel.safetech.enitities.Product;
import com.daniel.safetech.repositories.ProductRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @TestConfiguration
    static class ProductServiceImplTestContextConfiguration {
        @Bean
        public ProductServiceImpl productService() {
            return new ProductServiceImpl();
        }
    }

    @Autowired
    private ProductServiceImpl productService;
    @MockBean
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        List<Product> products = new ArrayList<>();
        products.add(createProduct());
        products.add(createProduct());
        products.add(createProduct());

        Mockito.when(productRepository.findAll())
                .thenReturn(products);
        Mockito.when(productRepository.findById(any()))
                .thenReturn(Optional.of(createProduct()));
        Mockito.when(productRepository.save(any()))
                .thenReturn(createProduct());
    }

    @Test
    public void shouldListALlProducts() {
        List<Product> productList = productService.listAllProducts();

        assertThat(productList.size(), Matchers.greaterThan(0));
    }

    @Test
    public void shouldGetProductById() {
        Optional<Product> product = productService.getProductById(1);

        assertThat(product.get().getId(), Matchers.equalTo(1));
    }

    @Test
    public void shouldSaveProduct() {
        Product product = new Product();
        product.setName("product");

        Product savedProduct = productService.saveProduct(product);

        assertThat(savedProduct.getId(), Matchers.notNullValue());
    }

    private Product createProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Product 1");
        product.setDescription("description");
        product.setPrice("50");
        return product;
    }

    private Boolean returnTrue() {
        return true;
    }
}
