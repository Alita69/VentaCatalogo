package edu.tecnologico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.tecnologico.model.Cliente;
import edu.tecnologico.model.Usuario;
import edu.tecnologico.service.UsuarioService;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // Página de login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "auth/login";
    }

    // Página de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("cliente", new Cliente());
        return "auth/registro";
    }

    // Registro de usuario con rol y cliente
    @PostMapping("/registro")
    public String registrarUsuario(
        @ModelAttribute("usuario") Usuario usuario,
        @ModelAttribute("cliente") Cliente cliente,
        @RequestParam("rol") String rolSeleccionado) {

        // ✅ Toda la lógica se maneja desde el servicio
        usuarioService.registrarUsuarioConRol(usuario, cliente, rolSeleccionado);
        return "redirect:/login?registroExitoso";
    }
}
