package edu.tecnologico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tecnologico.model.Cliente;
import edu.tecnologico.service.ClienteService;

@Controller
@RequestMapping("/clientes")
@PreAuthorize("hasRole('ADMINISTRADOR')")

public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "cliente/listaClientes";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/formCliente";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute("cliente") Cliente cliente) {
        clienteService.guardar(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id);
        if (cliente == null) return "redirect:/clientes";
        model.addAttribute("cliente", cliente);
        return "cliente/formCliente";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Long id) {
        clienteService.eliminar(id);
        return "redirect:/clientes";
    }
}
