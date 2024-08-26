package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM shop.product";
        try (Statement allProductStatement = dataSource.getConnection().createStatement()) {
            ResultSet allProductResult = allProductStatement.executeQuery(query);
            while (allProductResult.next()) {
                Product curProduct = new Product(allProductResult.getLong("id"), allProductResult.getString("name"),
                        allProductResult.getLong("price"));
                productList.add(curProduct);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return productList;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String query = "SELECT * FROM shop.product WHERE id = " + id;
        try (Statement productStatement = dataSource.getConnection().createStatement()) {
            ResultSet productResult = productStatement.executeQuery(query);
            if (!productResult.next())
                return Optional.empty();
            return Optional.of(new Product(id, productResult.getString("name"), productResult.getLong("price")));
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        String query = "UPDATE shop.product SET id = ?, name = ?, price = ?";
        try (PreparedStatement updateStatement = dataSource.getConnection().prepareStatement(query)) {
            updateStatement.setLong(1, product.getId());
            updateStatement.setString(2, product.getName());
            updateStatement.setLong(3, product.getPrice());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void save(Product product) {
        String query = "INSERT INTO shop.product(id, name, price) VALUES(?, ?, ?)";
        try (PreparedStatement saveStatement = dataSource.getConnection().prepareStatement(query)) {
            saveStatement.setLong(1, product.getId());
            saveStatement.setString(2, product.getName());
            saveStatement.setLong(3, product.getPrice());
            saveStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM shop.product WHERE id = " + id;
        try (PreparedStatement deleteStatement = dataSource.getConnection().prepareStatement(query)) {
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
