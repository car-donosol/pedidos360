package com.pedidos360.orders.infrastructure.adapter.in.rest;

import com.pedidos360.orders.application.port.in.CreateOrderCommand;
import com.pedidos360.orders.application.port.in.CreateOrderUseCase;
import com.pedidos360.orders.domain.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request,
                                                 @AuthenticationPrincipal Jwt jwt) {
        CreateOrderCommand command = new CreateOrderCommand(
                jwt.getSubject(), // customerId autenticado, no confiable desde el cliente
                request.branchId(),
                request.productId(),
                request.quantity(),
                request.dispatchType()
        );

        Order created = createOrderUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.from(created));
    }
}