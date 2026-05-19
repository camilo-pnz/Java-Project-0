package uts.edu.java.proyecto.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import uts.edu.java.proyecto.modelo.Categoria;
import uts.edu.java.proyecto.repositorio.CategoriaRepositorio;

@Service
public class CategoriaServicio implements ICategoriaServicio {

    @Autowired
    private CategoriaRepositorio repositorio;

    @Override
    public List<Categoria> listar() {
        return repositorio.findAll();
    }

    @Override
    public Categoria listarId(Integer id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return repositorio.save(categoria);
    }

    @Override
    public void delete(Integer id) {
        repositorio.deleteById(id);
    }
}