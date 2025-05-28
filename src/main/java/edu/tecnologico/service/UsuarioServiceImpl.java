package edu.tecnologico.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.tecnologico.model.Cliente;
import edu.tecnologico.model.Perfil;
import edu.tecnologico.model.Usuario;
import edu.tecnologico.repository.ClienteRepository;
import edu.tecnologico.repository.PerfilRepository;
import edu.tecnologico.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PerfilRepository perfilRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registrarUsuarioConRol(Usuario usuario, Cliente cliente, String rol) {
        usuario.setEstatus(1);
        usuario.setFechaRegistro(new Date());

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Perfil perfil = perfilRepo.findByPerfil(rol.toUpperCase());
        if (perfil == null) {
            perfil = new Perfil();
            perfil.setPerfil(rol.toUpperCase());
            perfil = perfilRepo.save(perfil); 
        }

        usuario.agregarPerfil(perfil);
        usuarioRepo.save(usuario);

        cliente.setNombre(usuario.getNombre());
        cliente.setCorreo(usuario.getUsername());
        clienteRepo.save(cliente);
    }
}
