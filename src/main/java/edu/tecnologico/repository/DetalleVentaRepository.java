package edu.tecnologico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.tecnologico.model.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    List<DetalleVenta> findByVentaId(Long ventaId); // ← necesario
}

