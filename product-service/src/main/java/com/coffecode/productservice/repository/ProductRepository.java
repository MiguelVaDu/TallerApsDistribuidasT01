package com.coffecode.productservice.repository;

import com.coffecode.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Spring Data JPA crea las implementaciones automáticamente
    // Puedes añadir métodos de consulta personalizados aquí si los necesitas
}