package com.umb.taller.domain;

import java.math.BigDecimal;

public class PremiumCustomer extends Customer {
    public PremiumCustomer(String id, String name, BigDecimal creditLimit) {
        super(id, name, creditLimit);
    }

    @Override
    public BigDecimal discountFor(Order order) {
        BigDecimal subtotal = order.lines().stream()
                .map(OrderLine::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                
        // 10% de descuento si el subtotal supera 100
        if (subtotal.compareTo(new BigDecimal("100")) >= 0) {
            return subtotal.multiply(new BigDecimal("0.10"));
        }
        return BigDecimal.ZERO;
    }
}
