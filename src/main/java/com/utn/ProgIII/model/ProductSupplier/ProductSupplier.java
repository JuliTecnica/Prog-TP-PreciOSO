package com.utn.ProgIII.model.ProductSupplier;

import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Supplier.Supplier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "product_supplier")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Audited
public class ProductSupplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProductSupplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_supplier")
    @ToString.Exclude
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    @ToString.Exclude
    private Product product;

    @NotNull(message = "El costo no puede estar vacío")
    @Positive(message = "El costo no puede ser negativo")
    private Double cost;

    public ProductSupplier(Supplier supplier, Product product, Double cost){
        this.supplier = supplier;
        this.product = product;
        this.cost = cost;
    }

}
