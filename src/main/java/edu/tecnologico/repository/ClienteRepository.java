package edu.tecnologico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.tecnologico.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
