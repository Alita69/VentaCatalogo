package edu.tecnologico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tecnologico.model.Producto;
import edu.tecnologico.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }


    @Override
    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> buscarPorNombreYCategoria(String nombre, Long idCategoria) {
        if (nombre != null && !nombre.isEmpty() && idCategoria != null) {
            return productoRepository.findByNombreContainingIgnoreCaseAndCategoriaId(nombre, idCategoria);
        } else if (nombre != null && !nombre.isEmpty()) {
            return productoRepository.findByNombreContainingIgnoreCase(nombre);
        } else if (idCategoria != null) {
            return productoRepository.findByCategoriaId(idCategoria);
        } else {
            return productoRepository.findAll();
        }
    }
}
