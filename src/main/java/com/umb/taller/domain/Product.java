package com.umb.taller.domain;

import com.umb.taller.domain.exception.ValidationException;
import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final String id;
    private final String name;
    private final BigDecimal price;

    public Product(String id, String name, BigDecimal price) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.price = Objects.requireNonNull(price, "price cannot be null");
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("price cannot be negative");
        }
    }

    public String id() { return id; }
    public String name() { return name; }
    public BigDecimal price() { return price; }
}

