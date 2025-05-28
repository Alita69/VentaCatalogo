package edu.tecnologico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tecnologico.model.Venta;
import edu.tecnologico.repository.VentaRepository;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public Venta guardar(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Override
    public List<Venta> listarTodas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta buscarPorId(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        ventaRepository.deleteById(id);
    }
}
