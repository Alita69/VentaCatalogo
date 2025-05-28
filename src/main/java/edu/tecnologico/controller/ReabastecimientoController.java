package edu.tecnologico.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tecnologico.model.Reabastecimiento;
import edu.tecnologico.service.ProductoService;
import edu.tecnologico.service.ProveedorService;
import edu.tecnologico.service.ReabastecimientoService;

@Controller
@RequestMapping("/reabastecimientos")
@PreAuthorize("hasRole('ADMINISTRADOR')")

public class ReabastecimientoController {

    @Autowired
    private ReabastecimientoService reabastecimientoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public String listar(Model model) {
        List<Reabastecimiento> lista = reabastecimientoService.listarTodos();
        model.addAttribute("reabastecimientos", lista);
        return "reabastecimiento/listaReabastecimientos";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("reabastecimiento", new Reabastecimiento());
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("proveedores", proveedorService.listarTodos());
        return "reabastecimiento/formularioReabastecimiento";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Reabastecimiento r) {
        r.setFecha(LocalDateTime.now());
        reabastecimientoService.guardar(r);
        return "redirect:/reabastecimientos";
    }
}
