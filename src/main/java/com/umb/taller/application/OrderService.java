package com.umb.taller.application;

import com.umb.taller.domain.Order;
import com.umb.taller.domain.OrderValidator;
import com.umb.taller.domain.Repository;

import java.util.List;

public class OrderService {
    private final Repository<Order, String> repository;
    private final List<OrderValidator> validators;

    public OrderService(Repository<Order, String> repo, List<OrderValidator> vals) {
        this.repository = repo;
        this.validators = vals;
    }

    public Order placeOrder(Order order) {
        validators.forEach(v -> v.validate(order));
        return repository.save(order);
    }
}
