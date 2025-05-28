package edu.tecnologico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.tecnologico.model.Entrega;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
	Entrega findByVentaId(Long ventaId);

}
