package com.pedidos360.notifications.domain.model;

import java.time.LocalDateTime;

public class AuditLog {
    
    private final String logId;
    private final String orderId;
    private final String branchId;
    private final String eventType;
    private final LocalDateTime processedAt;

    public AuditLog(String logId, String orderId, String branchId, String eventType, LocalDateTime processedAt) {
        this.logId = logId;
        this.orderId = orderId;
        this.branchId = branchId;
        this.eventType = eventType;
        this.processedAt = processedAt;
    }

    // Getters para todos los campos (Omitidos por brevedad, genéralos en VS Code)
    public String getLogId() { return logId; }
    public String getOrderId() { return orderId; }
    public String getBranchId() { return branchId; }
    public String getEventType() { return eventType; }
    public LocalDateTime getProcessedAt() { return processedAt; }
}