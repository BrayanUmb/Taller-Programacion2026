package com.umb.taller.infraestructure;

import com.umb.taller.domain.Order;
import com.umb.taller.domain.Repository;

import java.util.*;

public class InMemoryOrderRepository implements Repository<Order, String> {
    private final Map<String, Order> database = new HashMap<>();

    @Override
    public Order save(Order entity) {
        database.put(entity.id(), entity);
        return entity;
    }

    @Override
    public Optional<Order> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public void deleteById(String id) {
        database.remove(id);
    }
}

