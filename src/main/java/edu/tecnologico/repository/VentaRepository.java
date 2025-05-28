package edu.tecnologico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.tecnologico.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}
