package edu.tecnologico.service;

import java.util.List;

import edu.tecnologico.model.Venta;

public interface VentaService {
    Venta guardar(Venta venta);
    List<Venta> listarTodas();
    Venta buscarPorId(Long id);
    void eliminar(Long id);
}
