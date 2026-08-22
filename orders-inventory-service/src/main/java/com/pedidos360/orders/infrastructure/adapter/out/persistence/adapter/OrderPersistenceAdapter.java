package com.pedidos360.orders.infrastructure.adapter.out.persistence.adapter;

import com.pedidos360.orders.application.port.out.OrderRepositoryPort;
import com.pedidos360.orders.domain.model.Order;
import com.pedidos360.orders.infrastructure.adapter.out.persistence.entity.OrderJpaEntity;
import com.pedidos360.orders.infrastructure.adapter.out.persistence.repository.SpringDataOrderRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceAdapter implements OrderRepositoryPort {

    private final SpringDataOrderRepository repository;

    public OrderPersistenceAdapter(SpringDataOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity saved = repository.saveAndFlush(OrderJpaEntity.fromDomain(order));
        return saved.toDomainModel();
    }
}