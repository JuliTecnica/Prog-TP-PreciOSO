package com.utn.ProgIII.model.Product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idCategory;

    @NotBlank(message = "El nombre de la categoria no puede ser vacio")
    @Length(min = 3, max = 30, message = "El nombre de la categoria debe tener entre 3 y 30 caracteres")
    private String name;

}
