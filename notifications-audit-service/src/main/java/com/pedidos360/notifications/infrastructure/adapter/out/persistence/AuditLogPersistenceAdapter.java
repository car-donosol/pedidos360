package com.pedidos360.notifications.infrastructure.adapter.out.persistence;

import com.pedidos360.notifications.application.port.out.AuditLogRepositoryPort;
import com.pedidos360.notifications.domain.model.AuditLog;
import com.pedidos360.notifications.infrastructure.adapter.out.persistence.entity.AuditLogDocument;
import com.pedidos360.notifications.infrastructure.adapter.out.persistence.repository.SpringDataAuditLogRepository;
import org.springframework.stereotype.Component;

@Component
public class AuditLogPersistenceAdapter implements AuditLogRepositoryPort {

    private final SpringDataAuditLogRepository repository;

    public AuditLogPersistenceAdapter(SpringDataAuditLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(AuditLog auditLog) {
        // Mapeo: De objeto de Dominio a Documento de Base de Datos
        AuditLogDocument document = new AuditLogDocument(
                auditLog.getLogId(),
                auditLog.getOrderId(),
                auditLog.getBranchId(),
                auditLog.getEventType(),
                auditLog.getProcessedAt()
        );
        
        repository.save(document);
    }
}