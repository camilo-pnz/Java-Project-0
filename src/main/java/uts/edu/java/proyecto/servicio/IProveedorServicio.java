package uts.edu.java.proyecto.servicio;

import java.util.List;

import uts.edu.java.proyecto.modelo.Proveedor;

public interface IProveedorServicio {
    List<Proveedor> listar();
    Proveedor listarId(Integer id);
    Proveedor save(Proveedor proveedor);
    void delete(Integer id);
}