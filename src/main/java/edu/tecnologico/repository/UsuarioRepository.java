package edu.tecnologico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.tecnologico.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByUsername(String username);
}
