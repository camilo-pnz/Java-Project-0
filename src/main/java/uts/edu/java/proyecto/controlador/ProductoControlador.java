package uts.edu.java.proyecto.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uts.edu.java.proyecto.modelo.Producto;
import uts.edu.java.proyecto.servicio.ICategoriaServicio;
import uts.edu.java.proyecto.servicio.IProductoServicio;
import uts.edu.java.proyecto.servicio.IProveedorServicio;

@Controller
@RequestMapping("/views/productos")
public class ProductoControlador {

    @Autowired
    private IProductoServicio productoServicio;

    @Autowired
    private ICategoriaServicio categoriaServicio;

    @Autowired
    private IProveedorServicio proveedorServicio;

    @GetMapping("/")
    public String listar(Model model) {
        model.addAttribute("listaProductos", productoServicio.listar());
        return "views/productos/producto";
    }

    @GetMapping("/new")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("listaCategorias", categoriaServicio.listar());
        model.addAttribute("listaProveedores", proveedorServicio.listar());
        return "views/productos/nuevo_producto";
    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute("producto") Producto producto) {
        productoServicio.save(producto);
        return "redirect:/views/productos/";
    }

    @GetMapping("/listar/{id}")
    public String editar(@PathVariable int id, Model model) {
        model.addAttribute("producto", productoServicio.listarId(id));
        model.addAttribute("listaCategorias", categoriaServicio.listar());
        model.addAttribute("listaProveedores", proveedorServicio.listar());
        return "views/productos/editar_producto";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable int id) {
        productoServicio.delete(id);
        return "redirect:/views/productos/";
    }
}