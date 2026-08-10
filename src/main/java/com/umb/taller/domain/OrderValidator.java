package com.umb.taller.domain;

@FunctionalInterface
public interface OrderValidator {
    void validate(Order order);
}

