package com.pedidos360.notifications.infrastructure.adapter.in.messaging;

import com.pedidos360.notifications.application.port.in.ProcessOrderEventCommand;
import com.pedidos360.notifications.application.port.in.ProcessOrderEventUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderEventsKafkaConsumer {

    private final ProcessOrderEventUseCase processUseCase;

    // Inyección de dependencias por constructor (Buena práctica)
    public OrderEventsKafkaConsumer(ProcessOrderEventUseCase processUseCase) {
        this.processUseCase = processUseCase;
    }

    // Escucha el tópico "orders.events" con el grupo definido en application.properties
    @KafkaListener(topics = "orders.events", groupId = "notifications-audit-group")
    public void consumeOrderCreated(Map<String, Object> eventPayload) {
        try {
            // 1. Extraemos los datos del Payload que viene desde el otro microservicio
            String orderId = (String) eventPayload.get("orderId");
            String branchId = (String) eventPayload.get("branchId");
            String eventType = (String) eventPayload.get("eventType");

            // 2. Traducimos la infraestructura a nuestro lenguaje de Aplicación
            ProcessOrderEventCommand command = new ProcessOrderEventCommand(orderId, branchId, eventType);

            // 3. Ejecutamos el caso de uso
            processUseCase.process(command);

        } catch (Exception e) {
            // En un entorno productivo real, si un mensaje falla aquí,
            // lo enviaríamos a un "Dead Letter Topic" (DLT) para no bloquear la cola.
            System.err.println("Error procesando evento de Kafka: " + e.getMessage());
        }
    }
}