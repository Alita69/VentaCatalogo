package edu.tecnologico.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.tecnologico.model.Cliente;
import edu.tecnologico.model.DetalleVenta;
import edu.tecnologico.model.Entrega;
import edu.tecnologico.model.Pedido;
import edu.tecnologico.model.Producto;
import edu.tecnologico.model.Venta;
import edu.tecnologico.service.ClienteService;
import edu.tecnologico.service.DetalleVentaService;
import edu.tecnologico.service.EntregaService;
import edu.tecnologico.service.PedidoService;
import edu.tecnologico.service.ProductoService;
import edu.tecnologico.service.VentaService;

@Controller
@RequestMapping("/carrito")
@SessionAttributes({"carrito", "ventaActual"})
public class CarritoController {

    @Autowired private ProductoService productoService;
    @Autowired private ClienteService clienteService;
    @Autowired private VentaService ventaService;
    @Autowired private DetalleVentaService detalleVentaService;
    @Autowired private PedidoService pedidoService;
    @Autowired private EntregaService entregaService;

    @ModelAttribute("carrito")
    public List<DetalleVenta> carrito() {
        return new ArrayList<>();
    }

    @ModelAttribute("ventaActual")
    public Venta ventaActual() {
        return new Venta();
    }

    @PreAuthorize("hasRole('VENDEDOR')")
    @GetMapping("/productos")
    public String mostrarProductos(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "ventas/catalogoCarrito";
    }

    @PreAuthorize("hasRole('VENDEDOR')")
    @PostMapping("/agregar")
    public String agregarProducto(@RequestParam("productoId") Long productoId,
                                  @RequestParam("cantidad") int cantidad,
                                  @ModelAttribute("carrito") List<DetalleVenta> carrito) {
        Producto producto = productoService.buscarPorId(productoId);
        for (DetalleVenta existente : carrito) {
            if (existente.getProducto().getId().equals(productoId)) {
                existente.setCantidad(existente.getCantidad() + cantidad);
                return "redirect:/carrito/ver";
            }
        }
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(producto.getPrecio());
        carrito.add(detalle);
        return "redirect:/carrito/productos";
    }

    @PreAuthorize("hasRole('VENDEDOR')")
    @GetMapping("/ver")
    public String verCarrito(Model model,
                             @ModelAttribute("carrito") List<DetalleVenta> carrito) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "ventas/verCarrito";
    }

    @PreAuthorize("hasRole('VENDEDOR')")
    @PostMapping("/finalizar")
    public String finalizarVenta(@RequestParam("clienteId") Long clienteId,
                                 @ModelAttribute("carrito") List<DetalleVenta> carrito,
                                 Model model) {

        if (carrito == null || carrito.isEmpty()) {
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("mensaje", "⚠ El carrito está vacío. Agrega productos antes de confirmar la compra.");
            return "ventas/verCarrito";
        }

        Cliente cliente = clienteService.buscarPorId(clienteId);
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(calcularTotal(carrito));
        Venta ventaGuardada = ventaService.guardar(venta);

        boolean seGeneroPedido = false;
        for (DetalleVenta detalle : carrito) {
            Producto producto = productoService.buscarPorId(detalle.getProducto().getId());
            if (producto.getStock() != null && producto.getStock() >= detalle.getCantidad()) {
                producto.setStock(producto.getStock() - detalle.getCantidad());
                productoService.guardar(producto);
                detalle.setFaltante(false);
            } else {
                Pedido pedido = new Pedido();
                pedido.setProducto(producto);
                pedido.setProveedor(producto.getProveedor());
                pedido.setCantidad(detalle.getCantidad());
                pedido.setFecha(LocalDateTime.now());
                pedido.setEstado("Pendiente");
                pedido.setObservacion("Pedido generado automáticamente por falta de stock");
                pedidoService.guardar(pedido);
                detalle.setFaltante(true);
                seGeneroPedido = true;
            }
            detalle.setVenta(ventaGuardada);
            detalleVentaService.guardar(detalle);
        }

        Entrega entrega = new Entrega();
        entrega.setVenta(ventaGuardada);
        entrega.setFecha(LocalDateTime.now());
        entrega.setEstado("Pendiente");
        entrega.setObservacion("Entrega generada automáticamente luego de la venta.");
        entregaService.guardar(entrega);

        carrito.clear();

        if (seGeneroPedido) {
            model.addAttribute("mensaje", "⚠ Algunos productos no tenían stock. Se generaron pedidos automáticos.");
        }

        return "redirect:/carrito/ventas/lista";
    }

    private double calcularTotal(List<DetalleVenta> carrito) {
        return carrito.stream()
                .mapToDouble(d -> d.getPrecioUnitario() * d.getCantidad())
                .sum();
    }

    @PreAuthorize("hasAnyRole('VENDEDOR', 'ADMINISTRADOR')")
    @GetMapping("/ventas/lista")
    public String listarVentas(Model model) {
        model.addAttribute("ventas", ventaService.listarTodas());
        return "ventas/listaVentas";
    }

    @PreAuthorize("hasAnyRole('VENDEDOR', 'ADMINISTRADOR')")
    @GetMapping("/ventas/detalle/{id}")
    public String verDetalleVenta(@PathVariable Long id, Model model) {
        Venta venta = ventaService.buscarPorId(id);
        List<DetalleVenta> detalles = detalleVentaService.listarPorVentaId(id);
        model.addAttribute("venta", venta);
        model.addAttribute("detalles", detalles);
        return "ventas/detalleVenta";
    }

    @PreAuthorize("hasRole('VENDEDOR')")
    @GetMapping("/eliminar/{index}")
    public String eliminarDelCarrito(@PathVariable int index,
                                     @ModelAttribute("carrito") List<DetalleVenta> carrito) {
        if (index >= 0 && index < carrito.size()) {
            carrito.remove(index);
        }
        return "redirect:/carrito/ver";
    }
}
