package com.pvxdv.supplier.api;

import com.pvxdv.supplier.config.IntegrationTestBase;
import com.pvxdv.supplier.model.Category;
import com.pvxdv.supplier.model.Product;
import com.pvxdv.supplier.repository.CategoryRepository;
import com.pvxdv.supplier.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
class ProductControllerTest extends IntegrationTestBase {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void createNewProductSuccessfully() throws Exception {
        Long id = -1L;
        if (categoryRepository.findCategoryByName("vegetables").isPresent()) {
            id = categoryRepository.findCategoryByName("vegetables").get().getId();
        }

        String content = """
                {
                    "id": 0,
                    "name": "test name",
                    "description": "test description",
                    "price": 99.99,
                    "categoryId": %d
                  }
                """.formatted(id);

        ResultActions response = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.id").isNumber());
        response.andExpect(jsonPath("$.name").value("test name"));
        response.andExpect(jsonPath("$.description").value("test description"));
        response.andExpect(jsonPath("$.price").value("99.99"));
        response.andExpect(jsonPath("$.categoryId").value(id));
    }

    @Test
    void createNewProductFailedWith404() throws Exception {
        Long maxId = categoryRepository.findAll().stream().map(Category::getId).max(Comparator.naturalOrder()).get() + 1;

        String content = """
                {
                    "id": 0,
                    "name": "test name",
                    "description": "test description",
                    "price": 99.99,
                    "categoryId": %d
                  }
                """.formatted(maxId);

        ResultActions response = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        response.andExpect(status().isNotFound());
        response.andExpect(jsonPath("$.timestamp").isNotEmpty());
        response.andExpect(jsonPath("$.message").value("Category with id=%d not found".formatted(maxId)));
    }

    @ParameterizedTest
    @MethodSource("invalidProductsForTest")
    void createNewProductFailedWith400(String content) throws Exception {
        ResultActions response = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        response.andExpect(status().isBadRequest());
        response.andExpect(jsonPath("$.timestamp").isNotEmpty());
        response.andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void getProductsByFilterSuccessfully() throws Exception {
//        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
//        requestParams.add("name", "ca");
//        requestParams.add("description", "s");
//        requestParams.add("price", "50");
//        requestParams.add("offset", "0");
//        requestParams.add("limit", "1");
//
//        ResultActions response = mockMvc.perform(get("/api/v1/products/").params(requestParams));

//        ResultActions response = mockMvc
//                .perform(get("/api/v1/products?name={name}&?description={description}&?price={price}&?offset={offset}&?limit={limit}",
//                        "ca", "s", new BigDecimal(50), 0, 1));

//        ResultActions response = mockMvc.perform(get("/api/v1/products/")
//                .param("name", "ca")
//                .param("description", "s")
//                .param("price", "50")
//                .param("offset", "0")
//                .param("limit", "1"))
//                .andExpect(status().isOk());
//
//        response.andExpect(status().isOk());
//        response.andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    void getProductByIdSuccessfully() throws Exception {
        Category category = categoryRepository.save(new Category(0L, "test name"));
        Product product = productRepository
                .save(new Product(0L, "test name", "test description",
                        new BigDecimal("99.99"), category, null));

        String url = "/api/v1/products/" + product.getId();
        ResultActions response = mockMvc.perform(get(url));

        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.id").value(product.getId()));
        response.andExpect(jsonPath("$.name").value("test name"));
        response.andExpect(jsonPath("$.description").value("test description"));
        response.andExpect(jsonPath("$.price").value("99.99"));
        response.andExpect(jsonPath("$.categoryId").value(category.getId()));

        categoryRepository.deleteById(category.getId());
    }

    @Test
    void getProductByIdFailedWith404() throws Exception {
        Long maxProductId = productRepository.findAll().stream().map(Product::getId).max(Comparator.naturalOrder()).get() + 1;
        String url = "/api/v1/products/" + maxProductId;
        ResultActions response = mockMvc.perform(get(url));

        response.andExpect(status().isNotFound());
        response.andExpect(jsonPath("$.timestamp").isNotEmpty());
        response.andExpect(jsonPath("$.message").value("Product with id=%d not found".formatted(maxProductId)));
    }

    @Test
    void updateProductByIdSuccessfully() throws Exception {
        Category category = categoryRepository.save(new Category(0L, "test name"));
        Long productId = productRepository
                .save(new Product(0L, "test name", "test description",
                        new BigDecimal("99.99"), category, null)).getId();
        String url = "/api/v1/products/" + productId;

        String content = """
                {
                    "id": 0,
                    "name": "new name",
                    "description": "new description",
                    "price": 10.99,
                    "categoryId": %d
                  }
                """.formatted(category.getId());

        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());

        Product result = productRepository.findById(productId).get();
        assertEquals(result.getId(), productId);
        assertEquals(result.getName(), "new name");
        assertEquals(result.getDescription(), "new description");
        assertEquals(result.getCategory().getId(), category.getId());

        categoryRepository.deleteById(category.getId());
    }

    @Test
    void updateProductByIdFailedWith404() throws Exception {
        Long maxProductId = productRepository.findAll().stream().map(Product::getId).max(Comparator.naturalOrder()).get() + 1;
        Long maxCategoryId = categoryRepository.findAll().stream().map(Category::getId).max(Comparator.naturalOrder()).get();
        String url = "/api/v1/products/" + maxProductId;

        String content = """
                {
                    "id": 0,
                    "name": "test name",
                    "description": "test description",
                    "price": 99.99,
                    "categoryId": %d
                  }
                """.formatted(maxCategoryId);

        ResultActions response = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        response.andExpect(status().isNotFound());
        response.andExpect(jsonPath("$.timestamp").isNotEmpty());
        response.andExpect(jsonPath("$.message").value("Product with id=%d not found".formatted(maxProductId)));
    }

    @Test
    void deleteProductByIdSuccessfully() throws Exception {
        Category category = categoryRepository.save(new Category(0L, "test name"));
        Product product = new Product(0L, "test name", "test description", new BigDecimal("99.99"), category, null);
        Long productId = productRepository.save(product).getId();
        String url = "/api/v1/products/" + productId;

        mockMvc.perform(delete(url)).andExpect(status().isNoContent());

        categoryRepository.deleteById(category.getId());
    }

    @Test
    void deleteProductByIdFailedWith404() throws Exception {
        Long maxId = productRepository.findAll().stream().map(Product::getId).max(Comparator.naturalOrder()).get() + 1;
        String url = "/api/v1/products/" + maxId;

        ResultActions response = mockMvc.perform(delete(url));

        response.andExpect(status().isNotFound());
        response.andExpect(jsonPath("$.timestamp").isNotEmpty());
        response.andExpect(jsonPath("$.message").value("Product with id=%d not found".formatted(maxId)));
    }

    private static Stream<String> invalidProductsForTest() {
        List<String> invalidProducts = List.of(
                """
                        {
                            "id": -10,
                            "name": "test name",
                            "description": "test description",
                            "price": 99.99,
                            "categoryId": 5
                          }
                        """,
                """
                        {
                            "id": 0,
                            "name": "",
                            "description": "test description",
                            "price": 99.99,
                            "categoryId": 5
                          }
                        """,
                """
                        {
                            "id": 0,
                            "name": "test name",
                            "description": "",
                            "price": 99.99,
                            "categoryId": 5
                          }
                        """,
                """
                        {
                            "id": 0,
                            "name": "test name",
                            "description": "test description",
                            "price": -99.99,
                            "categoryId": 5
                          }
                        """,
                """
                        {
                            "id": 0,
                            "name": "test name",
                            "description": "test description",
                            "price": 99.99,
                            "categoryId": -10
                          }
                        """);
        return invalidProducts.stream();
    }
}