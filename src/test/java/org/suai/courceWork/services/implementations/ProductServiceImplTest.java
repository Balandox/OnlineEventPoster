package org.suai.courceWork.services.implementations;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.suai.courceWork.models.entities.Product;
import org.suai.courceWork.models.enums.Category;
import org.suai.courceWork.repositories.ProductRepository;
import org.suai.courceWork.services.implementations.ProductServiceImpl;
import org.suai.courceWork.services.interfaces.ProductService;
import org.suai.courceWork.utils.DateParser;

import javax.swing.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@SpringBootTest
class ProductServiceImplTest {

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp(){
        this.productRepository = Mockito.mock(ProductRepository.class);
        this.productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void getProductById() {
        Optional<Product> expProduct = Optional.of(new Product());
        expProduct.get().setId(1);

        given(this.productRepository.findById(1)).willReturn(expProduct);

        Product actProduct1 = this.productService.getProductById(1);

        assertEquals(expProduct.get(), actProduct1);


    }

    @Test
    void getAll() {
        List<Product> expProducts = new ArrayList<>();
        for(int i = 1; i <= 3; i++){
            Product product = new Product();
            product.setId(i);
            expProducts.add(product);
        }

        given(this.productRepository.findByOrderByTitle()).willReturn(expProducts);

        List<Product> actProducts = this.productService.getAll();

        assertEquals(3, actProducts.size());
        assertThat(actProducts).contains(expProducts.get(0));
    }



    @Test
    void searchProductByTitle() {
        Product expProduct = new Product();
        expProduct.setTitle("Концерт Drake");
        List<Product> expProductList = Collections.singletonList(expProduct);

        given(this.productRepository.findByTitleContainingIgnoreCase("dra")).willReturn(expProductList);

        assertEquals(expProductList, this.productService.searchProductByTitle("dra"));
    }

    @Test
    void getAllByDate() {
        Product expProduct1 = new Product();
        Date expDate1 = DateParser.getDate("2022-10-12");
        expProduct1.setDateOfEvent(expDate1);
        Product expProduct2 = new Product();
        Date expDate2 = DateParser.getDate("2023-11-15");
        expProduct2.setDateOfEvent(expDate2);

        List<Product> expProductList = Collections.singletonList(expProduct1);

        given(this.productRepository.findByDateOfEvent(expProduct1.getDateOfEvent())).willReturn(expProductList);

        assertEquals(1, this.productService.getAllByDate("2022-10-12").size());
        assertEquals(expProductList, this.productService.getAllByDate("2022-10-12"));
    }

    @Test
    void getAllByCategory() {

        Product expProduct1 = new Product();
        expProduct1.setCategory(Category.CINEMA);
        Product expProduct2 = new Product();
        expProduct2.setCategory(Category.THEATRE);

        List<Product> expProductList = Collections.singletonList(expProduct1);

        given(this.productRepository.findByCategory(Category.CINEMA)).willReturn(expProductList);

        assertEquals(1, this.productService.getAllByCategory(Category.CINEMA).size());
        assertEquals(expProductList, this.productService.getAllByCategory(Category.CINEMA));

    }

    @Test
    void searchProductWithCategory() {
        Product expProduct = new Product();
        expProduct.setTitle("Концерт Drake");
        expProduct.setCategory(Category.CONCERT);
        List<Product> expProductList = Collections.singletonList(expProduct);

        given(this.productRepository.findByTitleContainingIgnoreCaseAndCategory("dra",Category.CONCERT)).willReturn(expProductList);

        assertEquals(expProductList, this.productService.searchProductWithCategory(Category.CONCERT, "dra"));

    }
}