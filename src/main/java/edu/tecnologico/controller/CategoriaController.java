package edu.tecnologico.controller;

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

import edu.tecnologico.model.Categoria;
import edu.tecnologico.service.CategoriaService;

@Controller
@RequestMapping("/categorias")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "categoria/listaCategorias";
    }

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria/formCategoria";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("categoria") Categoria categoria, BindingResult result) {
        if (result.hasErrors()) {
            return "categoria/formCategoria";
        }
        categoriaService.guardar(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Categoria categoria = categoriaService.buscarPorId(id);
        if (categoria == null) return "redirect:/categorias";
        model.addAttribute("categoria", categoria);
        return "categoria/formCategoria";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        categoriaService.eliminar(id);
        return "redirect:/categorias";
    }
}
