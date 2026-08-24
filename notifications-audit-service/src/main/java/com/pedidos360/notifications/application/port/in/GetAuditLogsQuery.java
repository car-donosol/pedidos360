package com.pedidos360.notifications.application.port.in;

import java.util.List;
import com.pedidos360.notifications.domain.model.AuditLog;

public interface GetAuditLogsQuery {
    
    List<AuditLog> execute(String orderId);
    
}
