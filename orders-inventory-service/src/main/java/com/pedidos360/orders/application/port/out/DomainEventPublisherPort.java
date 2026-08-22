package com.pedidos360.orders.application.port.out;

import com.pedidos360.orders.domain.model.Order;

public interface DomainEventPublisherPort {
    // Para enviar el evento a Kafka/RabbitMQ
    void publishOrderCreatedEvent(Order order); 
}