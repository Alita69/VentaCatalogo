package edu.tecnologico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tecnologico.model.Entrega;
import edu.tecnologico.repository.EntregaRepository;

@Service
public class EntregaServiceImpl implements EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Override
    public Entrega guardar(Entrega entrega) {
        return entregaRepository.save(entrega);
    }

    @Override
    public List<Entrega> listarTodas() {
        return entregaRepository.findAll();
    }

    @Override
    public Entrega buscarPorId(Long id) {
        return entregaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        entregaRepository.deleteById(id);
    }
    @Override
    public Entrega buscarPorVenta(Long ventaId) {
        return entregaRepository.findByVentaId(ventaId);
    }

}
