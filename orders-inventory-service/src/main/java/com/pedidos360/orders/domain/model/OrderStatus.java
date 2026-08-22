package com.pedidos360.orders.domain.model;

public enum OrderStatus {
    PAGO_PENDIENTE,
    CONFIRMADO,       // pago OK + stock reservado, esperando que cocina lo tome
    EN_PREPARACION,   // cocina lo está preparando activamente
    LISTO,            // cocina terminó
    EN_CAMINO,        // solo aplica si dispatchType = DOMICILIO
    ENTREGADO,
    CANCELADO
}