package com.pedidos360.orders.infrastructure.adapter.out.persistence.repository;

import com.pedidos360.orders.infrastructure.adapter.out.persistence.entity.InventoryStockJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataInventoryRepository extends JpaRepository<InventoryStockJpaEntity, Long> {
    Optional<InventoryStockJpaEntity> findByBranchIdAndProductId(String branchId, String productId);
}