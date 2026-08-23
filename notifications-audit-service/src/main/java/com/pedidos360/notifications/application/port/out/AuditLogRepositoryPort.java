package com.pedidos360.notifications.application.port.out;

import com.pedidos360.notifications.domain.model.AuditLog;

public interface AuditLogRepositoryPort {
    void save(AuditLog auditLog);
}