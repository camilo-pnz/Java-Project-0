package uts.edu.java.proyecto.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uts.edu.java.proyecto.modelo.Usuario;
import uts.edu.java.proyecto.servicio.IUsuarioServicio;
@Controller
@RequestMapping("/views/usuarios")
public class UsuarioControlador {

    @Autowired
    private IUsuarioServicio servicio;

    @GetMapping("/")
    public String verIndex(Model model) {
        model.addAttribute("listaUsuarios", servicio.listar());
        return "views/usuarios/usuario"; 
    }

    @GetMapping("/new")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario()); 
        return "views/usuarios/nuevo_usuario";
    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute("usuario") Usuario usuario) {
        System.out.println("ID a guardar: " + usuario.getIdUsuario()); 
        servicio.save(usuario);
        return "redirect:/views/usuarios/";
    }
    @GetMapping("/listar/{idUsuario}")
    public String editar(@PathVariable int idUsuario, Model model) {
        model.addAttribute("usuario", servicio.listarId(idUsuario));
        return "views/usuarios/editar_usuario"; //
    }

    @GetMapping("/delete/{idUsuario}")
    public String eliminar(@PathVariable int idUsuario) {
        try {
            servicio.delete(idUsuario);
        } catch (Exception e) {
            // Si tiene ventas asociadas, solo lo desactiva
            Usuario usuario = servicio.listarId(idUsuario);
            if (usuario != null) {
                usuario.setActivo(false);
                servicio.save(usuario);
            }
        }
        return "redirect:/views/usuarios/";
    }
    
}