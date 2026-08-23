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

    private final AuditLogRepositoryPort auditLogRepository;

    public NotificationApplicationService(AuditLogRepositoryPort auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void process(ProcessOrderEventCommand command) {
        // 1. Instanciamos el modelo de dominio
        AuditLog log = new AuditLog(
                UUID.randomUUID().toString(), // Generamos un ID único para el log
                command.orderId(),
                command.branchId(),
                command.eventType(),
                LocalDateTime.now()
        );

        // 2. Guardamos la auditoría usando el puerto
        auditLogRepository.save(log);
        
        System.out.println("Auditoría guardada en MongoDB para el pedido: " + command.orderId());

        // TODO: Aquí en el futuro llamaremos al puerto de RabbitMQ para enviar notificaciones web
    }
}