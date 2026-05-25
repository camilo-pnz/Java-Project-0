package uts.edu.java.proyecto.servicio;

import java.util.List;
import uts.edu.java.proyecto.modelo.Venta;
import uts.edu.java.proyecto.modelo.DetalleVenta;

public interface IVentaServicio {
    List<Venta> listar();
    Venta listarId(Integer id);
    Venta save(Venta venta, List<DetalleVenta> detalles);
    void anular(Integer id);
}