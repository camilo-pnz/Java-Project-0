package uts.edu.java.proyecto.servicio;

import java.util.List;

import uts.edu.java.proyecto.modelo.Producto;

public interface IProductoServicio {
    List<Producto> listar();
    Producto listarId(Integer id);
    Producto save(Producto producto);
    void delete(Integer id);
}