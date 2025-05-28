package edu.tecnologico.service;

import java.util.List;

import edu.tecnologico.model.Cliente;

public interface ClienteService {
    List<Cliente> listarTodos();
    Cliente guardar(Cliente cliente);
    Cliente buscarPorId(Long id);
    void eliminar(Long id);
}
