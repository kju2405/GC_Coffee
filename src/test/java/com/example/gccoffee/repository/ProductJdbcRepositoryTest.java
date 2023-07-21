package com.example.gccoffee.repository;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductJdbcRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("상품을 추가할 수 있다.")
    void testInsert() {
        //given
        Product newProduct = new Product(UUID.randomUUID().toString(), "newProduct", Category.COFFEE, 1000L);

        //when
        productRepository.insert(newProduct);

        //then
        assertThat(productRepository.findAll().isEmpty()).isFalse();
        assertThat(productRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("id로 상품을 조회할 수 있다.")
    void testFindById() {
        //given
        String uuid = UUID.randomUUID().toString();
        Product newProduct = new Product(uuid, "newProduct", Category.COFFEE, 1000L);
        productRepository.insert(newProduct);

        //when
        Optional<Product> findProduct = productRepository.findById(uuid);

        //then
        assertThat(findProduct.get().getProductName()).isEqualTo("newProduct");
    }

    @Test
    @DisplayName("이름으로 상품을 조회할 수 있다.")
    void testFindByName() {
        //given
        String productName = "newProduct";
        Product newProduct = new Product(UUID.randomUUID().toString(), productName, Category.COFFEE, 1000L);
        productRepository.insert(newProduct);

        //when
        Optional<Product> findProduct = productRepository.findByName(productName);

        //then
        assertThat(findProduct.get().getPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("카테고리로 상품을 조회할 수 있다.")
    void testFindByCategory() {
        //given
        Product newProduct1 = new Product(UUID.randomUUID().toString(), "newProduct1", Category.COFFEE, 1000L);
        Product newProduct2 = new Product(UUID.randomUUID().toString(), "newProduct2", Category.COFFEE, 2000L);
        productRepository.insert(newProduct1);
        productRepository.insert(newProduct2);

        //when
        List<Product> listByCategory = productRepository.findByCategory(Category.COFFEE);

        //then
        assertThat(listByCategory.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품들을 전부 삭제할 수 있다.")
    void testDeleteAll() {
        //given
        Product newProduct1 = new Product(UUID.randomUUID().toString(), "newProduct1", Category.COFFEE, 1000L);
        Product newProduct2 = new Product(UUID.randomUUID().toString(), "newProduct2", Category.COFFEE, 2000L);
        productRepository.insert(newProduct1);
        productRepository.insert(newProduct2);

        //when
        List<Product> findAllList = productRepository.findAll();
        assertThat(findAllList.size()).isEqualTo(2);
        productRepository.deleteAll();

        //then
        assertThat(productRepository.findAll().isEmpty()).isTrue();
    }

    @Test
    @DisplayName("상품을 수정할 수 있다.")
    void testUpdate(){
        //given
        String uuid = UUID.randomUUID().toString();
        Product product = new Product(uuid, "newProduct", Category.COFFEE, 1000L);
        productRepository.insert(product);
        Product updatedProduct = new Product(uuid, "updatedProduct", Category.COFFEE, 1000L);

        //when
        productRepository.update(updatedProduct);

        //then
        Optional<Product> findProduct = productRepository.findById(uuid);
        assertThat(findProduct.get().getProductName()).isEqualTo("updatedProduct");
    }
}