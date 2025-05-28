package edu.tecnologico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.tecnologico.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
}
