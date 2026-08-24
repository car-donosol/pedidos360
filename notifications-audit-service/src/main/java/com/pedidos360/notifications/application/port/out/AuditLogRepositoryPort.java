package com.pedidos360.notifications.application.port.out;

import java.util.List;
import com.pedidos360.notifications.domain.model.AuditLog;

public interface AuditLogRepositoryPort {
    void save(AuditLog auditLog);

    // Método para buscar registros de auditoría por orderId
    List<AuditLog> findByOrderId(String orderId);
}