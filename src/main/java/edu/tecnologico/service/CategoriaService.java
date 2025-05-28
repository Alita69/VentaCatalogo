package edu.tecnologico.service;

import java.util.List;

import edu.tecnologico.model.Categoria;

public interface CategoriaService {
    List<Categoria> listarTodas();
    Categoria buscarPorId(Long id);
    void guardar(Categoria categoria);
    void eliminar(Long id);
}
