package com.pedidos360.notifications.application.port.in;

import com.pedidos360.notifications.application.exception.InvalidCommandException;

public record ProcessOrderEventCommand(
        String orderId,
        String branchId,
        String eventType
) {
    public ProcessOrderEventCommand {
        if (orderId == null || orderId.isBlank()) throw new InvalidCommandException("El orderId no puede ser nulo");
        if (eventType == null || eventType.isBlank()) throw new InvalidCommandException("El eventType no puede ser nulo");
    }
}