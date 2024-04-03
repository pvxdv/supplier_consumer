package com.pvxdv.supplier.api;

import com.pvxdv.supplier.config.IntegrationTestBase;
import com.pvxdv.supplier.model.Category;
import com.pvxdv.supplier.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Comparator;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest extends IntegrationTestBase {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void createNewCategorySuccessfully() throws Exception {
        String content = """
                {
                  "id": 0,
                  "name": "test name"
                }
                """;

        ResultActions response = mockMvc.perform(post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.id").isNumber());
        response.andExpect(jsonPath("$.name").value("test name"));

        Long id = categoryRepository.findCategoryByName("test name").get().getId();
        categoryRepository.deleteById(id);
    }

    @Test
    void createNewCategoryFailedWith409() throws Exception {
        String content = """
                {
                  "id": 0,
                  "name": "fruits"
                }
                """;

        ResultActions response = mockMvc.perform(post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        response.andExpect(status().isConflict());
        response.andExpect(jsonPath("$.timestamp").isNotEmpty());
        response.andExpect(jsonPath("$.message").value("Category with name=fruits already exist"));
    }

    @Test
    void createNewCategoryFailedWith400() throws Exception {
        String content = """
                {
                  "id": 0,
                  "name": ""
                }
                """;

        ResultActions response = mockMvc.perform(post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        response.andExpect(status().isBadRequest());
        response.andExpect(jsonPath("$.timestamp").isNotEmpty());
        response.andExpect(jsonPath("$.message").value("Validation error. name:must not be blank"));
    }

    @Test
    void getAllCategoriesSuccessfully() throws Exception {
        ResultActions response = mockMvc.perform(get("/api/v1/categories"));

        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.categories", hasSize(5)));
        response.andExpect(jsonPath("$.categories.[0].name").value("vegetables"));
        response.andExpect(jsonPath("$.categories.[1].name").value("fruits"));
        response.andExpect(jsonPath("$.categories.[2].name").value("seasonings"));
        response.andExpect(jsonPath("$.categories.[3].name").value("household chemicals"));
        response.andExpect(jsonPath("$.categories.[4].name").value("cereals"));
    }

    @Test
    void getCategoryByIdSuccessfully() throws Exception {
        Long id = -1L;
        if (categoryRepository.findCategoryByName("vegetables").isPresent()) {
            id = categoryRepository.findCategoryByName("vegetables").get().getId();
        }
        String url = "/api/v1/categories/" + id;
        ResultActions response = mockMvc.perform(get(url));

        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.id").value(id));
        response.andExpect(jsonPath("$.name").value("vegetables"));
    }

    @Test
    void getCategoryByIdFailedWith404() throws Exception {
        Long maxId = categoryRepository.findAll().stream().map(Category::getId).max(Comparator.naturalOrder()).get() + 1;
        String url = "/api/v1/categories/" + maxId;
        ResultActions response = mockMvc.perform(get(url));

        response.andExpect(status().isNotFound());
        response.andExpect(jsonPath("$.timestamp").isNotEmpty());
        response.andExpect(jsonPath("$.message").value("Category with id=%d not found".formatted(maxId)));
    }

    @Test
    void updateCategoryByIdSuccessfully() throws Exception {
        String content = """
                {
                  "id": 0,
                  "name": "books"
                }
                """;

        Long id = -1L;

        if (categoryRepository.findCategoryByName("vegetables").isPresent()) {
            id = categoryRepository.findCategoryByName("vegetables").get().getId();
        }

        String url = "/api/v1/categories/" + id;


        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());

        Category category = categoryRepository.findCategoryByName("books").get();

        assertEquals(category.getId(), id);
        assertEquals(category.getName(), "books");

        category.setName("vegetables");
        categoryRepository.save(category);
    }

    @Test
    void updateCategoryByIdFailedWith404() throws Exception {
        String content = """
                {
                  "id": 0,
                  "name": "books"
                }
                """;

        Long maxId = categoryRepository.findAll().stream().map(Category::getId).max(Comparator.naturalOrder()).get() + 1;
        String url = "/api/v1/categories/" + maxId;
        ResultActions response = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        response.andExpect(status().isNotFound());
        response.andExpect(jsonPath("$.timestamp").isNotEmpty());
        response.andExpect(jsonPath("$.message").value("Category with id=%d not found".formatted(maxId)));
    }

    @Test
    void deleteCategoryByIdSuccessfully() throws Exception {
        Category category = new Category(0L, "books");
        categoryRepository.save(category);

        if (categoryRepository.findCategoryByName("books").isPresent()) {
            Long id = categoryRepository.findCategoryByName("books").get().getId();
            String url = "/api/v1/categories/" + id;
            mockMvc.perform(delete(url))
                    .andExpect(status().isNoContent());
        } else {
            throw new Exception("Error in test: CategoryControllerTest.deleteCategoryByIdSuccessfully()");
        }
    }

    @Test
    void deleteCategoryByIdFailedWith404() throws Exception {
        Long maxId = categoryRepository.findAll().stream().map(Category::getId).max(Comparator.naturalOrder()).get() + 1;
        String url = "/api/v1/categories/" + maxId;
        ResultActions response = mockMvc.perform(delete(url));

        response.andExpect(status().isNotFound());
        response.andExpect(jsonPath("$.timestamp").isNotEmpty());
        response.andExpect(jsonPath("$.message").value("Category with id=%d not found".formatted(maxId)));
    }
}