package edu.tecnologico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tecnologico.model.Producto;
import edu.tecnologico.model.Reabastecimiento;
import edu.tecnologico.repository.ReabastecimientoRepository;

@Service
public class ReabastecimientoServiceImpl implements ReabastecimientoService {

    @Autowired
    private ReabastecimientoRepository repo;

    @Autowired
    private ProductoService productoService;

    @Override
    public Reabastecimiento guardar(Reabastecimiento r) {
        // Buscar el producto completo desde la base de datos usando el ID
        Producto productoCompleto = productoService.buscarPorId(r.getProducto().getId());

        if (productoCompleto == null) {
            throw new IllegalArgumentException("Producto no encontrado");
        }

        // Sumar al stock actual (evita null con 0 por defecto)
        int stockActual = (productoCompleto.getStock() != null) ? productoCompleto.getStock() : 0;
        productoCompleto.setStock(stockActual + r.getCantidad());

        // Guardar producto actualizado
        productoService.guardar(productoCompleto);

        // Asignar el objeto completo al reabastecimiento antes de guardar
        r.setProducto(productoCompleto);

        return repo.save(r);
    }


    @Override
    public List<Reabastecimiento> listarTodos() {
        return repo.findAll();
    }
}
