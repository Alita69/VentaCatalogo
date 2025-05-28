package edu.tecnologico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tecnologico.model.DetalleVenta;
import edu.tecnologico.repository.DetalleVentaRepository;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Override
    public DetalleVenta guardar(DetalleVenta detalle) {
        return detalleVentaRepository.save(detalle);
    }

    @Override
    public List<DetalleVenta> listarPorVentaId(Long ventaId) {
        return detalleVentaRepository.findByVentaId(ventaId); 
    }
    @Override
    public DetalleVenta buscarPorId(Long id) {
        return detalleVentaRepository.findById(id).orElse(null);
    }

}
