package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ProductsRepositoryJdbcImplTest {
    private DataSource dataSource;
    private ProductsRepositoryJdbcImpl productsRepository;
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(0L, "random minion", 10),
            new Product(1L, "purple minion", 20),
            new Product(2L, "kevin", 100),
            new Product(3L, "mike", 50),
            new Product(4L, "Gru", 100000),
            new Product(5L, "Dru", 1000)
    );
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(2L, "kevin", 100);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(3L, "mike", 40);
    final Product EXPECTED_SAVED_PRODUCT = new Product(6L, "big minion", 300);
    final Optional<Product> EXPECTED_DELETE_PRODUCT = Optional.empty();


    @BeforeEach
    public void init() {
        dataSource = new EmbeddedDatabaseBuilder()  // создание встроенной базы данных
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    public void findAllTest() throws SQLException {
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, productsRepository.findAll());
    }

    @Test
    public void findByIdTest() throws SQLException {
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, productsRepository.findById(2L).get());
        Assertions.assertEquals(Optional.empty(), productsRepository.findById(10L));
    }

    @Test
    public void updateTest() throws SQLException {
        Product product = productsRepository.findById(3L).get();
        product.setPrice(40);
        productsRepository.update(product);
        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT, productsRepository.findById(3L).get());
    }

    @Test
    public void saveTest() throws SQLException {
        Product product = new Product(6L, "big minion", 300);
        productsRepository.save(product);
        Assertions.assertEquals(EXPECTED_SAVED_PRODUCT, productsRepository.findById(6L).get());
    }

    @Test
    public void deleteTest() throws SQLException {
        productsRepository.delete(6L);
        Assertions.assertEquals(EXPECTED_DELETE_PRODUCT, productsRepository.findById(6L));
    }

}
