package uts.edu.java.proyecto.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uts.edu.java.proyecto.modelo.Categoria;
import uts.edu.java.proyecto.servicio.ICategoriaServicio;

@Controller
@RequestMapping("/views/categorias")
public class CategoriaControlador {

    @Autowired
    private ICategoriaServicio servicio;

    @GetMapping("/")
    public String listar(Model model) {
        model.addAttribute("listaCategorias", servicio.listar());
        return "views/categorias/categoria";
    }

    @GetMapping("/new")
    public String nuevo(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "views/categorias/nueva_categoria";
    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute("categoria") Categoria categoria) {
        servicio.save(categoria);
        return "redirect:/views/categorias/";
    }

    @GetMapping("/listar/{id}")
    public String editar(@PathVariable int id, Model model) {
        model.addAttribute("categoria", servicio.listarId(id));
        return "views/categorias/editar_categoria";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable int id) {
        servicio.delete(id);
        return "redirect:/views/categorias/";
    }
}