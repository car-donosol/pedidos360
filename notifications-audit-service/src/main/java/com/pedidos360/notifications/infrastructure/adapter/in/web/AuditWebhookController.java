package com.pedidos360.notifications.infrastructure.adapter.in.web;

import com.pedidos360.notifications.application.port.in.GetAuditLogsQuery;
import com.pedidos360.notifications.application.port.in.ProcessOrderEventUseCase;
import com.pedidos360.notifications.domain.model.AuditLog;
import com.pedidos360.notifications.application.port.in.ProcessOrderEventCommand;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditWebhookController {

    private final ProcessOrderEventUseCase processOrderEventUseCase;
    private final GetAuditLogsQuery getAuditLogsQuery;

    // Inyección de dependencias a través de la interfaz (Puerto de Entrada)
    public AuditWebhookController(ProcessOrderEventUseCase processOrderEventUseCase, GetAuditLogsQuery getAuditLogsQuery) {
        this.processOrderEventUseCase = processOrderEventUseCase;
        this.getAuditLogsQuery = getAuditLogsQuery;
    }

    @PostMapping("/simulate-event")
    public ResponseEntity<String> simulateKafkaEvent(@RequestBody ProcessOrderEventCommand command) {
        // Ejecutamos el caso de uso central
        processOrderEventUseCase.process(command);
        
        return ResponseEntity.ok("Evento de auditoría procesado y guardado en MongoDB con éxito.");
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<AuditLog>> getLogsByOrderId(@PathVariable String orderId) {
        List<AuditLog> logs = getAuditLogsQuery.execute(orderId);
        
        if (logs.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no hay registros
        }
        
        return ResponseEntity.ok(logs); // Devuelve 200 OK con la lista en JSON
    }
    
}