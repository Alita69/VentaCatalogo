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

import edu.tecnologico.model.Pedido;
import edu.tecnologico.service.PedidoService;
import edu.tecnologico.service.ProductoService;
import edu.tecnologico.service.ProveedorService;

@Controller
@RequestMapping("/pedidos")
@PreAuthorize("hasRole('ADMINISTRADOR')")

public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public String listar(Model model) {
        List<Pedido> lista = pedidoService.listarTodos();
        model.addAttribute("pedidos", lista);
        return "pedido/listaPedidos";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("proveedores", proveedorService.listarTodos());
        return "pedido/formularioPedido";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Pedido pedido) {
        pedido.setFecha(LocalDateTime.now());
        pedidoService.guardar(pedido);
        return "redirect:/pedidos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        pedidoService.eliminar(id);
        return "redirect:/pedidos";
    }

    @PostMapping("/estado/{id}")
    public String cambiarEstado(@PathVariable("id") Long id,
                                @RequestParam("estado") String estado) {
        Pedido pedido = pedidoService.buscarPorId(id);
        if (pedido != null) {
            pedido.setEstado(estado);
            pedidoService.guardar(pedido);
        }
        return "redirect:/pedidos";
    }
}
