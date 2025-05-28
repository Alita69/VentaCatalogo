package edu.tecnologico.service;

import java.util.List;

import edu.tecnologico.model.Producto;

public interface ProductoService {
    List<Producto> listarTodos();
    Producto buscarPorId(Long id);
    Producto guardar(Producto producto);
    void eliminar(Long id);

    List<Producto> buscarPorNombreYCategoria(String nombre, Long categoriaId);
}

