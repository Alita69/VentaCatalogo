package edu.tecnologico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirigirACatalogo() {
        return "redirect:/productos/catalogo";
    }
}
