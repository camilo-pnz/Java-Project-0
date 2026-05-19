package uts.edu.java.proyecto.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uts.edu.java.proyecto.modelo.Proveedor;
import uts.edu.java.proyecto.servicio.IProveedorServicio;

@Controller
@RequestMapping("/views/proveedores")
public class ProveedorControlador {

    @Autowired
    private IProveedorServicio servicio;

    @GetMapping("/")
    public String listar(Model model) {
        model.addAttribute("listaProveedores", servicio.listar());
        return "views/proveedor/proveedor";
    }

    @GetMapping("/new")
    public String nuevo(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "views/proveedor/nuevo_proveedor";
    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute("proveedor") Proveedor proveedor) {
        servicio.save(proveedor);
        return "redirect:/views/proveedores/";
    }

    @GetMapping("/listar/{id}")
    public String editar(@PathVariable int id, Model model) {
        model.addAttribute("proveedor", servicio.listarId(id));
        return "views/proveedor/editar_proveedor";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable int id) {
        servicio.delete(id);
        return "redirect:/views/proveedores/";
    }
}