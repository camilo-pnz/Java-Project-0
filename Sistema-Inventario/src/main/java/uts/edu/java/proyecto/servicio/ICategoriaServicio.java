package uts.edu.java.proyecto.servicio;

import java.util.List;

import uts.edu.java.proyecto.modelo.Categoria;

public interface ICategoriaServicio {
    List<Categoria> listar();
    Categoria listarId(Integer id);
    Categoria save(Categoria categoria);
    void delete(Integer id);
}