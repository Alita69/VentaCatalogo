package edu.tecnologico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tecnologico.model.Proveedor;
import edu.tecnologico.service.ProveedorService;

@Controller
@RequestMapping("/proveedores")
@PreAuthorize("hasRole('ADMINISTRADOR')")

public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public String listarProveedores(Model model) {
        List<Proveedor> proveedores = proveedorService.listarTodos();
        model.addAttribute("proveedores", proveedores);
        return "proveedor/listaProveedores";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "proveedor/formularioProveedor";
    }

    @PostMapping("/guardar")
    public String guardarProveedor(@ModelAttribute Proveedor proveedor) {
        proveedorService.guardar(proveedor);
        return "redirect:/proveedores";
    }

    @GetMapping("/editar/{id}")
    public String editarProveedor(@PathVariable Long id, Model model) {
        Proveedor proveedor = proveedorService.buscarPorId(id);
        model.addAttribute("proveedor", proveedor);
        return "proveedor/formularioProveedor";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminar(id);
        return "redirect:/proveedores";
    }
}
