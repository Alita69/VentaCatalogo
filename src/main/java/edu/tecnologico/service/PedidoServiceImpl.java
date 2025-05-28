package edu.tecnologico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tecnologico.model.Pedido;
import edu.tecnologico.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Pedido guardar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }
}
