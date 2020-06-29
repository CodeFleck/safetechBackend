package com.daniel.safetech.controllers;

import com.daniel.safetech.enitities.Product;
import com.daniel.safetech.services.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    ProductServiceImpl mockProductService;

    @Test
    public void shouldGetProducts() throws Exception {
        List<Product> products = createProducts();

        Mockito.when(mockProductService.listAllProducts()).thenReturn(products);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/products/")
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mvc.perform(builder);
        MvcResult result = resultActions.andReturn();
        String responseAsString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> itemList = objectMapper.readValue(responseAsString, List.class);

        assertThat(itemList.size(), Matchers.is(2));
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"Product 1\", \"password\":\"password\", \"type\":\"admin\"}");
        ResultActions resultActions = mvc.perform(builder);
        MvcResult result = resultActions.andReturn();

        assertThat(result.getResponse().getStatus(), Matchers.is(200));
    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        List<Product> products = createProducts();
        Mockito.doNothing().when(mockProductService).deleteProduct(any());
        Mockito.when(mockProductService.getProductById(any())).thenReturn(Optional.ofNullable(products.get(0)));
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/products/1");
        mvc.perform(builder);
        Mockito.verify(mockProductService, Mockito.times(1)).getProductById(any());
    }

    private List<Product> createProducts() {
        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Product 1");
        product1.setPrice("100");
        product1.setDescription("description");

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Product 2");
        product1.setPrice("100");
        product1.setDescription("description");

        products.add(product1);
        products.add(product2);

        return products;
    }
}
