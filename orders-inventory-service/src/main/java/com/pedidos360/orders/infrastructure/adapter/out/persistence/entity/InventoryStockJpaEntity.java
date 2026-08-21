package com.pedidos360.orders.infrastructure.adapter.out.persistence.entity;

import com.pedidos360.orders.domain.model.InventoryStock;
import jakarta.persistence.*;

@Entity
@Table(name = "inventory_stock")
public class InventoryStockJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_id", nullable = false)
    private String branchId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "current_quantity", nullable = false)
    private Integer currentQuantity;

    // EL NÚCLEO DE LA CONCURRENCIA
    @Version
    private Long version;

    // Constructor vacío requerido por JPA
    protected InventoryStockJpaEntity() {}

    public static InventoryStockJpaEntity fromDomain(InventoryStock domain) {
    InventoryStockJpaEntity entity = new InventoryStockJpaEntity();
    entity.id = domain.getTechnicalId();
    entity.branchId = domain.getBranchId();
    entity.productId = domain.getProductId();
    entity.currentQuantity = domain.getCurrentQuantity();
    entity.version = domain.getVersion();
    return entity;
}

public InventoryStock toDomainModel() {
    return new InventoryStock(this.id, this.version, this.branchId, this.productId, this.currentQuantity);
}

    // Getters para el Adaptador
    public Long getId() { return id; }
    public Long getVersion() { return version; }
}