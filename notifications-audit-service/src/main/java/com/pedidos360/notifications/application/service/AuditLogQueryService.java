package com.pedidos360.notifications.application.service;

import java.util.List;

import com.pedidos360.notifications.application.port.in.GetAuditLogsQuery;
import com.pedidos360.notifications.application.port.out.AuditLogRepositoryPort;
import com.pedidos360.notifications.domain.model.AuditLog;
import org.springframework.stereotype.Service;

@Service
public class AuditLogQueryService implements GetAuditLogsQuery {
    
    private final AuditLogRepositoryPort auditLogRepositoryPort;

    public AuditLogQueryService(AuditLogRepositoryPort auditLogRepositoryPort) {
        this.auditLogRepositoryPort = auditLogRepositoryPort;
    }

    @Override
    public List<AuditLog> execute(String orderId) {
        return auditLogRepositoryPort.findByOrderId(orderId);
    }
}