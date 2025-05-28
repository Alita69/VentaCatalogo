package edu.tecnologico.service;

import java.util.List;

import edu.tecnologico.model.Entrega;

public interface EntregaService {
    Entrega guardar(Entrega entrega);
    List<Entrega> listarTodas();
    Entrega buscarPorId(Long id);
    void eliminar(Long id);
    Entrega buscarPorVenta(Long ventaId);

}
