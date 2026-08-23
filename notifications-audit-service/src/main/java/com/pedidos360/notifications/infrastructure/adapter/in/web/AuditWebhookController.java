package com.pedidos360.notifications.infrastructure.adapter.in.web;

import com.pedidos360.notifications.application.port.in.ProcessOrderEventCommand;
import com.pedidos360.notifications.application.port.in.ProcessOrderEventUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditWebhookController {

    private final ProcessOrderEventUseCase processOrderEventUseCase;

    // Inyección de dependencias a través de la interfaz (Puerto de Entrada)
    public AuditWebhookController(ProcessOrderEventUseCase processOrderEventUseCase) {
        this.processOrderEventUseCase = processOrderEventUseCase;
    }

    @PostMapping("/simulate-event")
    public ResponseEntity<String> simulateKafkaEvent(@RequestBody ProcessOrderEventCommand command) {
        // Ejecutamos el caso de uso central
        processOrderEventUseCase.process(command);
        
        return ResponseEntity.ok("Evento de auditoría procesado y guardado en MongoDB con éxito.");
    }
}