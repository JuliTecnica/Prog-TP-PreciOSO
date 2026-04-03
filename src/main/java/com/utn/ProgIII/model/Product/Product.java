package com.utn.ProgIII.model.Product;

import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Audited
public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre del producto debe contener entre 3 y 50 caracteres")
    private String name;

    @OneToMany (mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    @NotAudited
    private List<ProductSupplier> productSuppliers;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private Float profitMargin;

    private Integer stock;
}
