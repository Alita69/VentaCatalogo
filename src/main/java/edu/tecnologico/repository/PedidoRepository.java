package edu.tecnologico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.tecnologico.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
