package edu.tecnologico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.tecnologico.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
