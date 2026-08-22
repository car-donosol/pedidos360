package com.pedidos360.orders.infrastructure.adapter.out.persistence.adapter;

import com.pedidos360.orders.application.port.out.InventoryRepositoryPort;
import com.pedidos360.orders.domain.exception.InsufficientStockException;
import com.pedidos360.orders.domain.exception.InventoryNotFoundException;
import com.pedidos360.orders.domain.model.InventoryStock;
import com.pedidos360.orders.infrastructure.adapter.out.persistence.entity.InventoryStockJpaEntity;
import com.pedidos360.orders.infrastructure.adapter.out.persistence.repository.SpringDataInventoryRepository;
import org.springframework.stereotype.Component;

@Component
public class InventoryPersistenceAdapter implements InventoryRepositoryPort {

    private final SpringDataInventoryRepository repository;

    public InventoryPersistenceAdapter(SpringDataInventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public InventoryStock findByBranchAndProduct(String branchId, String productId) {
        InventoryStockJpaEntity entity = repository.findByBranchIdAndProductId(branchId, productId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for branchId: " + branchId + " and productId: " + productId));

        return entity.toDomainModel();
    }

    @Override
    public void save(InventoryStock stock) {
        try {
            InventoryStockJpaEntity entity = InventoryStockJpaEntity.fromDomain(stock);
            repository.saveAndFlush(entity);
        } catch (org.springframework.dao.OptimisticLockingFailureException e) {
            throw new InsufficientStockException(
                "El stock fue modificado por otra compra simultánea. Intenta de nuevo.");
        }
    }
}