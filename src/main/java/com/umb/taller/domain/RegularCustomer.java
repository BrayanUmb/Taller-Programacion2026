package com.umb.taller.domain;

import java.math.BigDecimal;

public class RegularCustomer extends Customer {
    public RegularCustomer(String id, String name, BigDecimal creditLimit) {
        super(id, name, creditLimit);
    }

    @Override
    public BigDecimal discountFor(Order order) {
        return BigDecimal.ZERO; // Sin descuento
    }
}

