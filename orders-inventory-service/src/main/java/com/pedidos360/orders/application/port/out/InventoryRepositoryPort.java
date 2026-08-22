package com.pedidos360.orders.application.port.out;

import com.pedidos360.orders.domain.model.InventoryStock;

public interface InventoryRepositoryPort {

    InventoryStock findByBranchAndProduct(String branchId, String productId);

    void save(InventoryStock stock);
}