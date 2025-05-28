package edu.tecnologico.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestParam;

import edu.tecnologico.model.DetalleVenta;
import edu.tecnologico.model.Entrega;
import edu.tecnologico.model.Venta;
import edu.tecnologico.service.DetalleVentaService;
import edu.tecnologico.service.EntregaService;
import edu.tecnologico.service.VentaService;

@Controller
@RequestMapping("/entregas")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private DetalleVentaService detalleVentaService;

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'VENDEDOR')")
    @GetMapping
    public String listar(Model model) {
        List<Entrega> entregas = entregaService.listarTodas();
        model.addAttribute("entregas", entregas);
        return "entrega/listaEntregas";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("entrega", new Entrega());
        model.addAttribute("ventas", ventaService.listarTodas());
        return "entrega/formularioEntrega";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Entrega entrega) {
        entrega.setFecha(LocalDateTime.now());
        entregaService.guardar(entrega);
        return "redirect:/entregas";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        Entrega entrega = entregaService.buscarPorId(id);
        if (entrega != null && entrega.getVenta() != null) {
            entrega.setVenta(null);
            entregaService.guardar(entrega);
        }
        entregaService.eliminar(id);
        return "redirect:/entregas";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'VENDEDOR')")
    @GetMapping("/detalle/{id}")
    public String verDetalleEntrega(@PathVariable Long id, Model model) {
        Entrega entrega = entregaService.buscarPorId(id);
        Venta venta = entrega.getVenta();
        List<DetalleVenta> detalles = venta.getDetalles();
        model.addAttribute("entrega", entrega);
        model.addAttribute("detalles", detalles);
        return "entrega/detalleEntrega";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable Long id, Model model) {
        Entrega entrega = entregaService.buscarPorId(id);
        model.addAttribute("entrega", entrega);
        return "entrega/cambiarEstado";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/cambiarEstado")
    public String guardarCambioEstado(@RequestParam("id") Long id, @RequestParam("estado") String estado) {
        Entrega existente = entregaService.buscarPorId(id);
        if (existente != null) {
            existente.setEstado(estado);
            entregaService.guardar(existente);
        }
        return "redirect:/entregas";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/detalle/cambiarEstado/{id}")
    public String cambiarEstadoDetalle(@PathVariable("id") Long id,
                                       @RequestParam("estado") String nuevoEstado) {
        DetalleVenta detalle = detalleVentaService.buscarPorId(id);
        detalle.setEstadoEntrega(nuevoEstado);
        detalleVentaService.guardar(detalle);

        Long entregaId = entregaService.buscarPorVenta(detalle.getVenta().getId()).getId();
        return "redirect:/entregas/detalle/" + entregaId;
    }
}
