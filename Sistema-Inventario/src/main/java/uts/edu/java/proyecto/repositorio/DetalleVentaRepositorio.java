package uts.edu.java.proyecto.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import uts.edu.java.proyecto.modelo.DetalleVenta;

public interface DetalleVentaRepositorio extends JpaRepository<DetalleVenta, Integer> {
}