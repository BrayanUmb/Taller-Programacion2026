package com.umb.taller.domain;

import com.umb.taller.domain.exception.ValidationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private final String id;
    private final Customer customer;
    private final List<OrderLine> lines = new ArrayList<>();

    public Order(String id, Customer customer) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.customer = Objects.requireNonNull(customer, "customer cannot be null");
    }

    public void addItem(Product product, int quantity) {
        Objects.requireNonNull(product, "product cannot be null");
        if (quantity <= 0) {
            throw new ValidationException("quantity must be > 0");
        }
        lines.add(new OrderLine(product, quantity));
    }

    public BigDecimal total() {
        BigDecimal subtotal = lines.stream()
                .map(OrderLine::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return subtotal.subtract(customer.discountFor(this));
    }

    public String id() { return id; }
    public Customer customer() { return customer; }
    public List<OrderLine> lines() { return List.copyOf(lines); }
}

