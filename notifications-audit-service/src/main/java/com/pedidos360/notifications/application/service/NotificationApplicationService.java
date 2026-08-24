package com.pedidos360.notifications.application.service;

import com.pedidos360.notifications.application.port.in.ProcessOrderEventCommand;
import com.pedidos360.notifications.application.port.in.ProcessOrderEventUseCase;
import com.pedidos360.notifications.application.port.out.AuditLogRepositoryPort;
import com.pedidos360.notifications.domain.model.AuditLog;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NotificationApplicationService implements ProcessOrderEventUseCase {

    private final AuditLogRepositoryPort auditLogRepositoryPort;

    public NotificationApplicationService(AuditLogRepositoryPort auditLogRepositoryPort) {
        this.auditLogRepositoryPort = auditLogRepositoryPort;
    }

    @Override
    public void process(ProcessOrderEventCommand command) {
        // 1. Creamos la entidad de dominio pura aplicando reglas de negocio
        AuditLog auditLog = new AuditLog(
                UUID.randomUUID().toString(),
                command.orderId(),
                command.branchId(),
                command.eventType(),
                LocalDateTime.now()
        );

        // 2. Invocamos el puerto de salida para persistir
        auditLogRepositoryPort.save(auditLog);
    }
}