package com.coffecode.productservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products") // Nombre de la tabla en PostgreSQL
@Data // Lombok: genera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: genera constructor sin argumentos
@AllArgsConstructor // Lombok: genera constructor con todos los argumentos
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Para que PostgreSQL maneje el autoincremento
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer stock;
}