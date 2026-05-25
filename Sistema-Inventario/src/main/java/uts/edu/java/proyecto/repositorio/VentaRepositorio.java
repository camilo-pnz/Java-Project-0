package uts.edu.java.proyecto.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import uts.edu.java.proyecto.modelo.Venta;
import java.util.List;

public interface VentaRepositorio extends JpaRepository<Venta, Integer> {
    List<Venta> findByUsuario_IdUsuario(int idUsuario);
}