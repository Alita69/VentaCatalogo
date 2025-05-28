package edu.tecnologico.service;

import java.util.List;

import edu.tecnologico.model.Pedido;

public interface PedidoService {
    Pedido guardar(Pedido pedido);
    List<Pedido> listarTodos();
    Pedido buscarPorId(Long id);
    void eliminar(Long id);
}
