package com.umb.taller.domain;

import java.math.BigDecimal;

public abstract class Customer {
    protected final String id;
    protected final String name;
    protected final BigDecimal creditLimit;

    public Customer(String id, String name, BigDecimal creditLimit) {
        this.id = id;
        this.name = name;
        this.creditLimit = creditLimit;
    }

    public abstract BigDecimal discountFor(Order order);

    public String id() { return id; }
    public String name() { return name; }
    public BigDecimal creditLimit() { return creditLimit; }
}

