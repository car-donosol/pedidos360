package com.pedidos360.notifications.application.port.in;

public interface ProcessOrderEventUseCase {
    void process(ProcessOrderEventCommand command);
}