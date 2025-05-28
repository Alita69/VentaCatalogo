package edu.tecnologico.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "perfil")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String perfil;

    @ManyToMany(mappedBy = "perfiles")
    private List<Usuario> usuarios;

    // === Getters y Setters ===

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }

    @Override
    public String toString() {
        return "Perfil [id=" + id + ", perfil=" + perfil + "]";
    }
}
