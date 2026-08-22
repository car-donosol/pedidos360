package com.pedidos360.orders.domain.model;

import com.pedidos360.orders.domain.exception.InvalidOrderStateException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Order {
    private String orderId;
    private String customerId;
    private String branchId;
    private OrderStatus status;
    private DispatchType dispatchType;
    private List<OrderItem> items;
    private LocalDateTime createdAt;

    public static Order createNew(String customerId, String branchId, DispatchType dispatchType) {
        Order order = new Order();
        order.orderId = UUID.randomUUID().toString();
        order.customerId = customerId;
        order.branchId = branchId;
        order.status = OrderStatus.PAGO_PENDIENTE;
        order.dispatchType = dispatchType;
        order.items = new ArrayList<>();
        order.createdAt = LocalDateTime.now();
        return order;
    }

    public static Order reconstitute(String orderId, String customerId, String branchId,
                                OrderStatus status, DispatchType dispatchType,
                                List<OrderItem> items, LocalDateTime createdAt) {
        Order order = new Order();
        order.orderId = orderId;
        order.customerId = customerId;
        order.branchId = branchId;
        order.status = status;
        order.dispatchType = dispatchType;
        order.items = new ArrayList<>(items);
        order.createdAt = createdAt;
        return order;
    }

    public void addItem(String productId, Integer quantity, Double unitPrice) {
        if (this.status != OrderStatus.PAGO_PENDIENTE) {
            throw new InvalidOrderStateException("No se pueden agregar ítems a un pedido que ya no está pendiente de pago.");
        }
        this.items.add(new OrderItem(productId, quantity, unitPrice));
    }

    // Pago confirmado + stock ya reservado por el caso de uso -> espera a cocina
    public void confirmPayment() {
        requireStatus(OrderStatus.PAGO_PENDIENTE, "confirmar el pago");
        this.status = OrderStatus.CONFIRMADO;
    }

    // Cocina toma el pedido desde el KDS
    public void startPreparation() {
        requireStatus(OrderStatus.CONFIRMADO, "iniciar la preparación");
        this.status = OrderStatus.EN_PREPARACION;
    }

    public void markReady() {
        requireStatus(OrderStatus.EN_PREPARACION, "marcar como listo");
        this.status = OrderStatus.LISTO;
    }

    public void dispatch() {
        if (this.status != OrderStatus.LISTO) {
            throw new InvalidOrderStateException("Solo un pedido LISTO puede despacharse.");
        }
        this.status = (this.dispatchType == DispatchType.DOMICILIO) ? OrderStatus.EN_CAMINO : OrderStatus.ENTREGADO;
    }

    public void confirmDelivery() {
        requireStatus(OrderStatus.EN_CAMINO, "confirmar la entrega");
        this.status = OrderStatus.ENTREGADO;
    }

    // Solo se puede cancelar antes de que cocina empiece a prepararlo
    public void cancel() {
        if (this.status != OrderStatus.PAGO_PENDIENTE && this.status != OrderStatus.CONFIRMADO) {
            throw new InvalidOrderStateException("No se puede cancelar un pedido que ya está en preparación o más avanzado.");
        }
        this.status = OrderStatus.CANCELADO;
    }

    private void requireStatus(OrderStatus expected, String accion) {
        if (this.status != expected) {
            throw new InvalidOrderStateException("No se puede " + accion + " desde el estado " + this.status);
        }
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public String getBranchId() { return branchId; }
    public OrderStatus getStatus() { return status; }
    public DispatchType getDispatchType() { return dispatchType; }
    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); } // defensiva: nadie externo modifica la lista real
}