package uts.edu.java.proyecto.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uts.edu.java.proyecto.modelo.DetalleVenta;
import uts.edu.java.proyecto.modelo.Venta;
import uts.edu.java.proyecto.modelo.Usuario;
import uts.edu.java.proyecto.servicio.IProductoServicio;
import uts.edu.java.proyecto.servicio.IUsuarioServicio;
import uts.edu.java.proyecto.servicio.IVentaServicio;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/views/ventas")
public class VentaControlador {

    @Autowired
    private IVentaServicio ventaServicio;

    @Autowired
    private IProductoServicio productoServicio;

    @Autowired
    private IUsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String listar(Model model) {
        model.addAttribute("listaVentas", ventaServicio.listar());
        return "views/ventas/venta";
    }

    @GetMapping("/new")
    public String nueva(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("listaProductos", productoServicio.listar());
        return "views/ventas/nueva_venta";
    }

    @PostMapping("/save")
    public String guardar(
            @RequestParam("productoIds") List<Integer> productoIds,
            @RequestParam("cantidades") List<Integer> cantidades,
            Authentication authentication) {

        // Obtener usuario autenticado
    	Usuario usuario = usuarioServicio.listarPorUsername(authentication.getName());
        Venta venta = new Venta();
        venta.setUsuario(usuario);

        List<DetalleVenta> detalles = new ArrayList<>();
        for (int i = 0; i < productoIds.size(); i++) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(productoServicio.listarId(productoIds.get(i)));
            detalle.setCantidad(cantidades.get(i));
            detalles.add(detalle);
        }

        ventaServicio.save(venta, detalles);
        return "redirect:/views/ventas/";
    }

    @GetMapping("/anular/{id}")
    public String anular(@PathVariable int id) {
        ventaServicio.anular(id);
        return "redirect:/views/ventas/";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable int id, Model model) {
        model.addAttribute("venta", ventaServicio.listarId(id));
        return "views/ventas/detalle_venta";
    }
}