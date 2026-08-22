package com.pedidos360.orders.infrastructure.adapter.out.persistence.entity;

import com.pedidos360.orders.domain.model.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    private String id; // UUID generado por el dominio, no por la BD -> sin @GeneratedValue

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "branch_id", nullable = false)
    private String branchId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "dispatch_type")
    private DispatchType dispatchType;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItemJpaEntity> items = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Igual que en Inventory: cuando cocina/repartidor empiecen a mutar el
    // estado del mismo pedido, esto evita el mismo tipo de carrera que ya resolvimos.
    @Version
    private Long version;

    protected OrderJpaEntity() {}

    public static OrderJpaEntity fromDomain(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.id = order.getOrderId();
        entity.customerId = order.getCustomerId();
        entity.branchId = order.getBranchId();
        entity.status = order.getStatus();
        entity.dispatchType = order.getDispatchType();
        entity.createdAt = order.getCreatedAt();
        entity.items = order.getItems().stream()
                .map(i -> new OrderItemJpaEntity(i.getProductId(), i.getQuantity(), i.getUnitPrice()))
                .collect(Collectors.toList());
        return entity;
    }

    public Order toDomainModel() {
        List<OrderItem> domainItems = this.items.stream()
                .map(i -> new OrderItem(i.getProductId(), i.getQuantity(), i.getUnitPrice()))
                .collect(Collectors.toList());
        return Order.reconstitute(id, customerId, branchId, status, dispatchType, domainItems, createdAt);
    }
}