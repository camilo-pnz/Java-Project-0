package uts.edu.java.proyecto.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

    @GetMapping("/login")
    public String mostrarLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
        }
        if (logout != null) {
            model.addAttribute("mensaje", "Has cerrado sesión correctamente.");
        }
        return "login";
    }

    @GetMapping("/home")
    public String mostrarHome(Model model) {
        model.addAttribute("titulo", "Sistema de Inventario y Ventas");
        return "home";
    }

    @GetMapping("/")
    public String raiz() {
        return "redirect:/home";
    }
    @GetMapping("/bienvenida")
    public String bienvenida() {
        return "bienvenida";
    }
}