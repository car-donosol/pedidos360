package com.pedidos360.notifications.infrastructure.adapter.out.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;

@Document(collection = "audit_logs")
public class AuditLogDocument {

    @Id
    private String logId;

    @Field("order_id")
    private String orderId;

    @Field("branch_id")
    private String branchId;

    @Field("event_type")
    private String eventType;

    @Field("processed_at")
    private LocalDateTime processedAt;

    // Constructor, Getters y Setters...

    public AuditLogDocument(String logId, String orderId, String branchId, String eventType, LocalDateTime processedAt) {
        this.logId = logId;
        this.orderId = orderId;
        this.branchId = branchId;
        this.eventType = eventType;
        this.processedAt = processedAt;
    }

    public String getLogId() {
        return logId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getBranchId() {
        return branchId;
    }

    public String getEventType() {
        return eventType;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }
}