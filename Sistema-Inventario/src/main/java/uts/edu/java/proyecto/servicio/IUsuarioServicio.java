package uts.edu.java.proyecto.servicio;

import java.util.List;

import uts.edu.java.proyecto.modelo.Usuario;

public interface IUsuarioServicio {
    public List<Usuario> listar();
    public Usuario listarId(Integer id);
    public Usuario save(Usuario usuario);
    public void delete(Integer id);
    Usuario listarPorUsername(String username);
}