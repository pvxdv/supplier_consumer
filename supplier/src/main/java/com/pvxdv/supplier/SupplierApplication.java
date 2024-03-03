package com.pvxdv.supplier;

import com.pvxdv.supplier.model.Category;
import com.pvxdv.supplier.model.Product;
import com.pvxdv.supplier.repository.CategoryRepository;
import com.pvxdv.supplier.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@Slf4j
@SpringBootApplication
public class SupplierApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplierApplication.class, args);
    }

    @Bean
    public CommandLineRunner clr(CategoryRepository categoryRepository,
                                 ProductRepository productRepository) {
        return args -> {
            categoryRepository.deleteAll();
            productRepository.deleteAll();

            log.info("Load categories.....");



            Category vegetables = Category.builder().name("vegetables").build();
            Category fruits = Category.builder().name("fruits").build();
            Category seasonings = Category.builder().name("seasonings").build();
            Category householdChemicals = Category.builder().name("household chemicals").build();
            Category cereals = Category.builder().name("cereals").build();

            categoryRepository.save(vegetables);
            categoryRepository.save(fruits);
            categoryRepository.save(seasonings);
            categoryRepository.save(householdChemicals);
            categoryRepository.save(cereals);

            log.info("Load products.....");
            productRepository.save(Product.builder()
                    .name("carrot").description("some text").price(new BigDecimal("20,01")).category(vegetables)
                    .build());
            productRepository.save(Product.builder()
                    .name("potato").description("some text").price(new BigDecimal("10,56")).category(vegetables)
                    .build());
            productRepository.save(Product.builder()
                    .name("orange").description("some text").price(new BigDecimal("200,00")).category(fruits)
                    .build());
            productRepository.save(Product.builder()
                    .name("apple").description("some text").price(new BigDecimal("80,01")).category(fruits)
                    .build());
            productRepository.save(Product.builder()
                    .name("cinnamon").description("some text").price(new BigDecimal("50,30")).category(seasonings)
                    .build());
            productRepository.save(Product.builder()
                    .name("black pepper").description("some text").price(new BigDecimal("68,80")).category(seasonings)
                    .build());
            productRepository.save(Product.builder()
                    .name("soap").description("some text").price(new BigDecimal("150,40")).category(householdChemicals)
                    .build());
            productRepository.save(Product.builder()
                    .name("toothpaste").description("some text").price(new BigDecimal("199,99")).category(householdChemicals)
                    .build());
            productRepository.save(Product.builder()
                    .name("rice").description("some text").price(new BigDecimal("99,00")).category(cereals)
                    .build());
            productRepository.save(Product.builder()
                    .name("buckwheat").description("some text").price(new BigDecimal("105,00")).category(cereals)
                    .build());
        };
    }
}
