package uts.edu.java.proyecto.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import uts.edu.java.proyecto.modelo.Proveedor;
import uts.edu.java.proyecto.repositorio.ProveedorRepositorio;

@Service
public class ProveedorServicio implements IProveedorServicio {

    @Autowired
    private ProveedorRepositorio repositorio;

    @Override
    public List<Proveedor> listar() {
        return repositorio.findAll();
    }

    @Override
    public Proveedor listarId(Integer id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    public Proveedor save(Proveedor proveedor) {
        return repositorio.save(proveedor);
    }

    @Override
    public void delete(Integer id) {
        repositorio.deleteById(id);
    }
}