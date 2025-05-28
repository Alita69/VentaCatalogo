package edu.tecnologico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.tecnologico.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
	List<Producto> findByNombreContainingIgnoreCase(String nombre);
	List<Producto> findByCategoriaId(Long idCategoria);
	List<Producto> findByNombreContainingIgnoreCaseAndCategoriaId(String nombre, Long idCategoria);

}
