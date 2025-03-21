package com.altev.ecommerce.e2etests;

import com.altev.ecommerce.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    static Long createdProductId;

    @Test
    @Order(1)
    void testCreateProduct() throws Exception {
            String loginPayload = """
        {
          "email": "admin@admin.com",
          "password": "123123"
        }
        """;
        MvcResult loginResult = mockMvc.perform(post("api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginPayload))
                .andExpect(status().isOk())
                .andReturn();
        String loginResponseBody = loginResult.getResponse().getContentAsString();
        String token = JsonPath.read(loginResponseBody, "$.token"); // using JsonPath or ObjectMapper
        System.out.println("loginResponseBody: " + loginResponseBody);
        System.out.println("token: "+token);
        ProductDTO product = new ProductDTO();
        product.setName("E2E Salad");
        product.setPrice(15.99);
        product.setQuantity(100);
        product.setRating(4);

        MvcResult result = mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("E2E Salad"))
            .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ProductDTO created = objectMapper.readValue(responseBody, ProductDTO.class);
        createdProductId = created.getId();
    }

    @Test
    @Order(2)
    void testGetProductById() throws Exception {
        mockMvc.perform(get("/products/" + createdProductId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(createdProductId))
            .andExpect(jsonPath("$.name").value("E2E Salad"));
    }

    @Test
    @Order(3)
    void testUpdateProduct() throws Exception {
        ProductDTO updatedProduct = new ProductDTO();
        updatedProduct.setId(createdProductId);
        updatedProduct.setName("Updated E2E Salad");

        mockMvc.perform(patch("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated E2E Salad"));
    }

    @Test
    @Order(4)
    void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", not(empty())))
            .andExpect(jsonPath("$[?(@.name == 'Updated E2E Salad')]").exists());
    }

    @Test
    @Order(5)
    void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/products/" + createdProductId))
            .andExpect(status().isOk());

        mockMvc.perform(get("/products/" + createdProductId))
            .andExpect(status().is4xxClientError()); // expect 404 or similar
    }
}
