package uts.edu.java.proyecto.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import uts.edu.java.proyecto.modelo.Producto;
import uts.edu.java.proyecto.repositorio.ProductoRepositorio;

@Service
public class ProductoServicio implements IProductoServicio {

    @Autowired
    private ProductoRepositorio repositorio;

    @Override
    public List<Producto> listar() {
        return repositorio.findAll();
    }

    @Override
    public Producto listarId(Integer id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    public Producto save(Producto producto) {
        return repositorio.save(producto);
    }

    @Override
    public void delete(Integer id) {
        repositorio.deleteById(id);
    }
}