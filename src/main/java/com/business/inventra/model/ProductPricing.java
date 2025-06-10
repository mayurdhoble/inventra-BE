/*
package com.business.inventra.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "product_pricing")
public class ProductPricing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "p_code", nullable = false)
    private String pCode;

    @Column(name = "p_unit", nullable = false)
    private String pUnit;

    @Column(name = "price_per_unit", nullable = false)
    private BigDecimal pricePerUnit;

    @Column(name = "effective_date", nullable = false)
    private LocalDateTime effectiveDate;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    @Column(name = "org_id", nullable = false)
    private String orgId;

    @ManyToOne
    @JoinColumn(name = "p_code", referencedColumnName = "p_code", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "p_unit", referencedColumnName = "id", insertable = false, updatable = false)
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "org_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Organization organization;
}
*/ 