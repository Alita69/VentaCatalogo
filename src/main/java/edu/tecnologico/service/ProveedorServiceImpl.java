package edu.tecnologico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tecnologico.model.Proveedor;
import edu.tecnologico.repository.ProveedorRepository;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public Proveedor guardar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor buscarPorId(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }
}
