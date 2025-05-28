package edu.tecnologico.service;

import java.util.List;

import edu.tecnologico.model.Proveedor;

public interface ProveedorService {
    Proveedor guardar(Proveedor proveedor);
    List<Proveedor> listarTodos();
    Proveedor buscarPorId(Long id);
    void eliminar(Long id);
}
