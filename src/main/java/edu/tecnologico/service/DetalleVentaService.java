package edu.tecnologico.service;

import java.util.List;

import edu.tecnologico.model.DetalleVenta;

public interface DetalleVentaService {
    DetalleVenta guardar(DetalleVenta detalle);
    List<DetalleVenta> listarPorVentaId(Long ventaId);
    DetalleVenta buscarPorId(Long id);

}
