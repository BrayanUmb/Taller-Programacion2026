package com.umb.taller;

import com.umb.taller.infraestructure.InMemoryOrderRepository;
import com.umb.taller.application.OrderService;
import com.umb.taller.domain.*;
import com.umb.taller.domain.exception.BusinessRuleException;
import com.umb.taller.domain.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderServiceTest {

    private Repository<Order, String> mockRepo;
    private OrderService service;

    @BeforeEach
    void setup() {
        mockRepo = new InMemoryOrderRepository();
        
        OrderValidator emptyValidator = order -> {
            if (order.lines().isEmpty()) {
                throw new ValidationException("Empty order");
            }
        };

        OrderValidator limitValidator = order -> {
            if (order.total().compareTo(order.customer().creditLimit()) > 0) {
                throw new BusinessRuleException("Exceeds credit limit");
            }
        };

        service = new OrderService(mockRepo, List.of(emptyValidator, limitValidator));
    }

    private Customer regularCustomer() {
        return new RegularCustomer("C-1", "John", new BigDecimal("500"));
    }

    private Customer customerWithLimit(String limit) {
        return new RegularCustomer("C-2", "LimitUser", new BigDecimal(limit));
    }

    private Customer premiumCustomer() {
        return new PremiumCustomer("C-3", "Alice", new BigDecimal("1000"));
    }

    private Product product(String price) {
        return new Product("P-1", "Test Product", new BigDecimal(price));
    }

    @Test
    void shouldThrowValidationExceptionForEmptyOrder() {
        Order order = new Order("O-1", regularCustomer());
        assertThatThrownBy(() -> service.placeOrder(order))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Empty");
    }

    @Test
    void shouldThrowBusinessRuleForExceedingCreditLimit() {
        Order order = new Order("O-2", customerWithLimit("50"));
        order.addItem(product("100"), 1);
        assertThatThrownBy(() -> service.placeOrder(order))
                .isInstanceOf(BusinessRuleException.class);
    }

    @Test
    void shouldPersistValidOrder() {
        Order order = new Order("O-3", regularCustomer());
        order.addItem(product("50"), 1);
        Order saved = service.placeOrder(order);
        assertThat(mockRepo.findById("O-3")).contains(saved);
    }

    @Test
    void premiumCustomerShouldGetDiscount() {
        Order order = new Order("O-4", premiumCustomer());
        order.addItem(product("100"), 1);
        assertThat(order.total()).isLessThan(new BigDecimal("100"));
    }

    @Test
    void shouldHandleMultipleLineItems() {
        Order order = new Order("O-5", regularCustomer());
        order.addItem(product("10"), 2);
        order.addItem(product("15"), 3);
        assertThat(order.total()).isEqualByComparingTo(new BigDecimal("65"));
    }

    // Repositorio en memoria para pruebas
    private static class InMemoryOrderRepository implements Repository<Order, String> {
        private final List<Order> orders = new ArrayList<>();

        @Override
        public Order save(Order entity) {
            orders.add(entity);
            return entity;
        }

        @Override
        public Optional<Order> findById(String id) {
            return orders.stream().filter(o -> o.id().equals(id)).findFirst();
        }

        @Override
        public List<Order> findAll() { return orders; }

        @Override
        public void deleteById(String id) { orders.removeIf(o -> o.id().equals(id)); }
    }
}

