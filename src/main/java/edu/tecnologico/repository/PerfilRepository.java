package edu.tecnologico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.tecnologico.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    Perfil findByPerfil(String perfil);
}
