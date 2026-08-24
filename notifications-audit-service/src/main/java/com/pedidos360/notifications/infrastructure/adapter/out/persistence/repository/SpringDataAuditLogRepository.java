package com.pedidos360.notifications.infrastructure.adapter.out.persistence.repository;

import com.pedidos360.notifications.infrastructure.adapter.out.persistence.entity.AuditLogDocument;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataAuditLogRepository extends MongoRepository<AuditLogDocument, String> {

    // Método para buscar registros de auditoría por orderId
    List<AuditLogDocument> findByOrderId(String orderId);
}