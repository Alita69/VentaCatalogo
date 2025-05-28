package edu.tecnologico.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.tecnologico.model.Producto;
import edu.tecnologico.service.CategoriaService;
import edu.tecnologico.service.ProductoService;
import edu.tecnologico.service.ProveedorService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProveedorService proveedorService;

    // Visible para ADMINISTRADOR y VENDEDOR
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'VENDEDOR')")
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "producto/listaProductos";
    }

    // Solo ADMINISTRADOR puede crear
    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("proveedores", proveedorService.listarTodos());
        return "producto/formProducto";
    }

    // Solo ADMINISTRADOR puede guardar
    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public String guardarProducto(@Valid @ModelAttribute("producto") Producto producto,
                                  BindingResult result,
                                  @RequestParam("imagenArchivo") MultipartFile archivo,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listarTodas());
            model.addAttribute("proveedores", proveedorService.listarTodos());
            return "producto/formProducto";
        }

        if (!archivo.isEmpty()) {
            try {
                String nombreArchivo = archivo.getOriginalFilename();
                producto.setImagen(nombreArchivo);

                Path ruta = Paths.get("src/main/resources/static/imagenes");
                Files.createDirectories(ruta);
                Files.copy(archivo.getInputStream(), ruta.resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productoService.guardar(producto);
        return "redirect:/productos";
    }

    // Solo ADMINISTRADOR puede editar
    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public String editar(@PathVariable("id") Long id, Model model) {
        Producto producto = productoService.buscarPorId(id);
        if (producto == null) {
            return "redirect:/productos";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("proveedores", proveedorService.listarTodos());
        return "producto/formProducto";
    }

    // Solo ADMINISTRADOR puede eliminar
    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public String eliminar(@PathVariable("id") Long id) {
        productoService.eliminar(id);
        return "redirect:/productos";
    }

    // Catálogo visible públicamente
    @GetMapping("/catalogo")
    public String verCatalogo(@RequestParam(required = false) String nombre,
                              @RequestParam(required = false) Long categoriaId,
                              Model model) {
        model.addAttribute("productos", productoService.buscarPorNombreYCategoria(nombre, categoriaId));
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("nombre", nombre);
        model.addAttribute("categoriaId", categoriaId);
        return "producto/catalogo";
    }

    // Detalle de producto público
    @GetMapping("/catalogo/{id}")
    public String verDetalle(@PathVariable("id") Long id, Model model) {
        Producto producto = productoService.buscarPorId(id);
        if (producto == null) {
            return "redirect:/productos/catalogo";
        }
        model.addAttribute("producto", producto);
        return "producto/detalleProducto";
    }
}
