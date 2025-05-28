package edu.tecnologico.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String username; 
    private String password;

    private Integer estatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarioPerfil",
        joinColumns = @JoinColumn(name = "idUsuario"),
        inverseJoinColumns = @JoinColumn(name = "idPerfil")
    )
    private List<Perfil> perfiles;

    // === Métodos de apoyo ===
    public void agregarPerfil(Perfil perfil) {
        if (perfiles == null) {
            perfiles = new ArrayList<>();
        }
        perfiles.add(perfil);
    }

    // === Getters y Setters ===

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getEstatus() { return estatus; }
    public void setEstatus(Integer estatus) { this.estatus = estatus; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public List<Perfil> getPerfiles() { return perfiles; }
    public void setPerfiles(List<Perfil> perfiles) { this.perfiles = perfiles; }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", username=" + username + "]";
    }
}
