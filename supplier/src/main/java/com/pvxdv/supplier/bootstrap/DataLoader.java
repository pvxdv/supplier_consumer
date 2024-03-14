package com.pvxdv.supplier.bootstrap;

import com.pvxdv.supplier.model.Category;
import com.pvxdv.supplier.model.Comment;
import com.pvxdv.supplier.model.Product;
import com.pvxdv.supplier.repository.CategoryRepository;
import com.pvxdv.supplier.repository.CommentRepository;
import com.pvxdv.supplier.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.findAll().isEmpty()) {
            loadData();
        }
    }

    private void loadData() {
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
        Product carrot = Product.builder().name("carrot")
                .description("Carrot - a source of vitamin A and carotene, have a “slightly spicy”," +
                        " “nutty” taste, and the “juicy-crisp” texture of carrots makes them suitable" +
                        " for eating raw or cooked.")
                .price(new BigDecimal("20.01"))
                .category(vegetables)
                .build();
        Product potato = Product.builder()
                .name("potato")
                .description("Potato - a universal ingredient that can easily be combined with" +
                        " various foods: vegetables, fish, meat and even loganberry jam.")
                .price(new BigDecimal("10.56"))
                .category(vegetables)
                .build();
        Product orange = Product.builder()
                .name("orange")
                .description("Orange - a natural hybrid of grapefruit and tangerine," +
                        " has many beneficial properties known to folk medicine, and is rich in vitamin C")
                .price(new BigDecimal("200.00"))
                .category(fruits)
                .build();
        Product apple = Product.builder()
                .name("apple")
                .description("Apple - a juicy fruit of the apple tree, which is eaten fresh and baked," +
                        " serves as a raw material in cooking and for making drinks.")
                .price(new BigDecimal("80.01"))
                .category(fruits)
                .build();
        Product cinnamon = Product.builder()
                .name("cinnamon")
                .description("Cinnamon - a spice with a tart aroma and a slightly pungent taste," +
                        " most often sold ground or in the form of sticks. It is made from the bark of an evergreen" +
                        " deciduous tree of the Laurel family, the genus Korichnikov")
                .price(new BigDecimal("50.30"))
                .category(seasonings)
                .build();
        Product blackPepper = Product.builder()
                .name("black pepper")
                .description("Black pepper - the king of spices. It is the dried fruit of an Indian" +
                        " vine plant of the species Piper nigrum, native to the Malabar coast of India.")
                .price(new BigDecimal("68.80"))
                .category(seasonings)
                .build();
        Product soap = Product.builder()
                .name("soap").description(" Soap - a detergent for hygiene cosmetics or household chemicals," +
                        " the main component of which is water-soluble salts of fatty acids - the product" +
                        " of the interaction of alkali with natural or synthetic fatty acids.")
                .price(new BigDecimal("150.40"))
                .category(householdChemicals)
                .build();
        Product toothpaste = Product.builder()
                .name("toothpaste").description("Toothpaste - an oral hygiene product in the form of a paste," +
                        " gel or powder, which is used to clean teeth from plaque in order to prevent caries.")
                .price(new BigDecimal("199.99"))
                .category(householdChemicals)
                .build();
        Product rice = Product.builder()
                .name("rice").description("Rice - a genus of annual and perennial plants of the grass family." +
                        " One of the oldest grain crops; The region of primary domestication" +
                        " of rice is northern Indochina.")
                .price(new BigDecimal("99.00"))
                .category(cereals)
                .build();
        Product buckwheat = Product.builder()
                .name("buckwheat").description("Buckwheat - a grain product obtained from the seeds of" +
                        " the buckwheat plant (Fagopyrum esculentum). Buckwheat is widespread and popular" +
                        " in many countries, especially in Eastern Europe and Russia.")
                .price(new BigDecimal("105.00"))
                .category(cereals)
                .build();

        productRepository.save(carrot);
        productRepository.save(potato);
        productRepository.save(orange);
        productRepository.save(apple);
        productRepository.save(cinnamon);
        productRepository.save(blackPepper);
        productRepository.save(soap);
        productRepository.save(toothpaste);
        productRepository.save(rice);
        productRepository.save(buckwheat);

        log.info("Load comments & rating.....");
        Comment carrotCommentOne = Comment.builder().rating(5).text("good price").product(carrot).build();
        Comment carrotCommentTwo = Comment.builder().rating(4).text("nice").product(carrot).build();

        Comment potatoCommentOne = Comment.builder().rating(2).text("very dirty").product(potato).build();
        Comment potatoCommentTwo = Comment.builder().rating(4).text("good").product(potato).build();

        Comment orangeCommentOne = Comment.builder().rating(5).text("sweet").product(orange).build();
        Comment orangeCommentTwo = Comment.builder().rating(5).text("cool").product(orange).build();

        Comment appleCommentOne = Comment.builder().rating(4).text("good price").product(apple).build();
        Comment appleCommentTwo = Comment.builder().rating(4).text("nice").product(apple).build();

        Comment cinnamonCommentOne = Comment.builder().rating(3).text("good price").product(cinnamon).build();
        Comment cinnamonCommentTwo = Comment.builder().rating(4).text("not good").product(cinnamon).build();

        Comment blackPepperCommentOne = Comment.builder().rating(2).text("not recommended").product(blackPepper).build();
        Comment blackPepperCommentTwo = Comment.builder().rating(1).text("bad").product(blackPepper).build();

        Comment soapCommentOne = Comment.builder().rating(1).text(".....").product(soap).build();
        Comment soapCommentTwo = Comment.builder().rating(5).text("nice").product(soap).build();

        Comment toothpasteCommentOne = Comment.builder().rating(2).text("so so").product(toothpaste).build();
        Comment toothpasteCommentTwo = Comment.builder().rating(2).text("not recommended").product(toothpaste).build();

        Comment riceCommentOne = Comment.builder().rating(5).text("").product(rice).build();
        Comment riceCommentTwo = Comment.builder().rating(4).text("nice").product(rice).build();

        Comment buckwheatCommentOne = Comment.builder().rating(3).text("normal").product(buckwheat).build();
        Comment buckwheatCommentTwo = Comment.builder().rating(3).text("").product(buckwheat).build();

        commentRepository.save(carrotCommentOne);
        commentRepository.save(carrotCommentTwo);
        commentRepository.save(potatoCommentOne);
        commentRepository.save(potatoCommentTwo);
        commentRepository.save(orangeCommentOne);
        commentRepository.save(orangeCommentTwo);
        commentRepository.save(appleCommentOne);
        commentRepository.save(appleCommentTwo);
        commentRepository.save(cinnamonCommentOne);
        commentRepository.save(cinnamonCommentTwo);
        commentRepository.save(blackPepperCommentOne);
        commentRepository.save(blackPepperCommentTwo);
        commentRepository.save(soapCommentOne);
        commentRepository.save(soapCommentTwo);
        commentRepository.save(toothpasteCommentOne);
        commentRepository.save(toothpasteCommentTwo);
        commentRepository.save(riceCommentOne);
        commentRepository.save(riceCommentTwo);
        commentRepository.save(buckwheatCommentOne);
        commentRepository.save(buckwheatCommentTwo);
    }
}
