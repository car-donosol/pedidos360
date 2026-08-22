package com.pedidos360.orders.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedidos360.orders.application.port.in.CreateOrderCommand;
import com.pedidos360.orders.application.port.in.CreateOrderUseCase;
import com.pedidos360.orders.application.port.out.DomainEventPublisherPort;
import com.pedidos360.orders.application.port.out.InventoryRepositoryPort;
import com.pedidos360.orders.application.port.out.OrderRepositoryPort;
import com.pedidos360.orders.domain.model.InventoryStock;
import com.pedidos360.orders.domain.model.Order;

@Service
public class OrderApplicationService implements CreateOrderUseCase {

    private final InventoryRepositoryPort inventoryPort;
    private final OrderRepositoryPort orderPort;
    private final DomainEventPublisherPort eventPublisherPort;

    // Inyección de dependencias por constructor (obligatorio, no uses @Autowired en propiedades)
    public OrderApplicationService(InventoryRepositoryPort inventoryPort, 
                                   OrderRepositoryPort orderPort, 
                                   DomainEventPublisherPort eventPublisherPort) {
        this.inventoryPort = inventoryPort;
        this.orderPort = orderPort;
        this.eventPublisherPort = eventPublisherPort;
    }

    @Override
    @Transactional // Fundamental: Toda esta orquestación es atómica (ACID)
    public Order execute(CreateOrderCommand command) {
        
        // 1. Recuperar estado (Llama al puerto, no sabe si es Postgres o MySQL)
        InventoryStock stock = inventoryPort.findByBranchAndProduct(command.branchId(), command.productId());

        // 2. Ejecutar lógica de negocio PURA (El dominio valida si hay stock suficiente)
        stock.deductStock(command.quantity());

        // 3. Persistir el stock modificado (Si alguien más lo modificó al mismo tiempo, 
        // aquí explotará el OptimisticLockException que atraparemos más adelante)
        inventoryPort.save(stock);

        // 4. Crear el agregado Order y confirmarlo (el stock YA se reservó arriba)
        Order newOrder = Order.createNew(command.customerId(), command.branchId(), command.dispatchType());
        newOrder.addItem(command.productId(), command.quantity(), 5000.0); // TODO: ProductPort para precio real
        newOrder.confirmPayment(); // <- transición explícita: refleja que el stock ya está reservado

        Order savedOrder = orderPort.save(newOrder);

        // 5. Notificar al resto del sistema vía mensajería
        eventPublisherPort.publishOrderCreatedEvent(savedOrder);

        return savedOrder;
    }
}