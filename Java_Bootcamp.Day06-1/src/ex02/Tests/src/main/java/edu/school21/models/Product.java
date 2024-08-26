package edu.school21.models;

import com.google.common.base.Objects;

public class Product {
    private long id;
    private String name;
    private long price;

    public Product(long id, String name, long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Product product = (Product) obj;
        return id == product.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, price);
    }

    @Override
    public String toString() {
        return "Product: id=" + id + ", name=" + name + ", price=" + price;
    }
}
