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
                    .name("carrot")
                    .description("Carrot - a source of vitamin A and carotene, have a “slightly spicy”," +
                            " “nutty” taste, and the “juicy-crisp” texture of carrots makes them suitable" +
                            " for eating raw or cooked.")
                    .price(new BigDecimal("20.01"))
                    .category(vegetables)
                    .build());
            productRepository.save(Product.builder()
                    .name("potato")
                    .description("Potato - a universal ingredient that can easily be combined with" +
                            " various foods: vegetables, fish, meat and even loganberry jam.")
                    .price(new BigDecimal("10.56"))
                    .category(vegetables)
                    .build());
            productRepository.save(Product.builder()
                    .name("orange")
                    .description("Orange - a natural hybrid of grapefruit and tangerine," +
                            " has many beneficial properties known to folk medicine, and is rich in vitamin C")
                    .price(new BigDecimal("200.00"))
                    .category(fruits)
                    .build());
            productRepository.save(Product.builder()
                    .name("apple")
                    .description("Apple - a juicy fruit of the apple tree, which is eaten fresh and baked," +
                            " serves as a raw material in cooking and for making drinks.")
                    .price(new BigDecimal("80.01"))
                    .category(fruits)
                    .build());
            productRepository.save(Product.builder()
                    .name("cinnamon")
                    .description("Cinnamon - a spice with a tart aroma and a slightly pungent taste," +
                            " most often sold ground or in the form of sticks. It is made from the bark of an evergreen" +
                            " deciduous tree of the Laurel family, the genus Korichnikov")
                    .price(new BigDecimal("50.30"))
                    .category(seasonings)
                    .build());
            productRepository.save(Product.builder()
                    .name("black pepper")
                    .description("Black pepper - the king of spices. It is the dried fruit of an Indian" +
                            " vine plant of the species Piper nigrum, native to the Malabar coast of India.")
                    .price(new BigDecimal("68.80"))
                    .category(seasonings)
                    .build());
            productRepository.save(Product.builder()
                    .name("soap").description(" Soap - a detergent for hygiene cosmetics or household chemicals," +
                            " the main component of which is water-soluble salts of fatty acids - the product" +
                            " of the interaction of alkali with natural or synthetic fatty acids.")
                    .price(new BigDecimal("150.40"))
                    .category(householdChemicals)
                    .build());
            productRepository.save(Product.builder()
                    .name("toothpaste").description("Toothpaste - an oral hygiene product in the form of a paste," +
                            " gel or powder, which is used to clean teeth from plaque in order to prevent caries.")
                    .price(new BigDecimal("199.99"))
                    .category(householdChemicals)
                    .build());
            productRepository.save(Product.builder()
                    .name("rice").description("Rice - a genus of annual and perennial plants of the grass family." +
                            " One of the oldest grain crops; The region of primary domestication" +
                            " of rice is northern Indochina.")
                    .price(new BigDecimal("99.00"))
                    .category(cereals)
                    .build());
            productRepository.save(Product.builder()
                    .name("buckwheat").description("Buckwheat - a grain product obtained from the seeds of" +
                            " the buckwheat plant (Fagopyrum esculentum). Buckwheat is widespread and popular" +
                            " in many countries, especially in Eastern Europe and Russia.")
                    .price(new BigDecimal("105.00"))
                    .category(cereals)
                    .build());
        };
    }
}
