package com.pedidos360.orders.domain.model;

import com.pedidos360.orders.domain.exception.InsufficientStockException;

public class InventoryStock {
    // Identidad técnica para el bloqueo optimista.
    // El dominio nunca la usa en sus reglas de negocio, solo la "carga" para el adaptador.
    private final Long technicalId;
    private final Long version;
    private final String branchId;
    private final String productId;
    private Integer currentQuantity;

    public InventoryStock(String branchId, String productId, Integer currentQuantity) {
        this(null, null, branchId, productId, currentQuantity);
    }

    public InventoryStock(Long technicalId, Long version, String branchId, String productId, Integer currentQuantity) {
        this.technicalId = technicalId;
        this.version = version;
        this.branchId = branchId;
        this.productId = productId;
        this.currentQuantity = currentQuantity;
    }

    public void deductStock(Integer requestedAmount) {
        if (requestedAmount <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        if (this.currentQuantity < requestedAmount) {
            throw new InsufficientStockException("Stock insuficiente para el producto en esta sucursal");
        }
        this.currentQuantity -= requestedAmount;
    }

    public String getBranchId() { return branchId; }
    public String getProductId() { return productId; }
    public Integer getCurrentQuantity() { return currentQuantity; }
    public Long getTechnicalId() { return technicalId; }
    public Long getVersion() { return version; }
}