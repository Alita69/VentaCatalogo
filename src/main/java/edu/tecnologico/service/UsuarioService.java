package edu.tecnologico.service;

import edu.tecnologico.model.Cliente;
import edu.tecnologico.model.Usuario;

public interface UsuarioService {
    void registrarUsuarioConRol(Usuario usuario, Cliente cliente, String rol);
}
