package uts.edu.java.proyecto.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import uts.edu.java.proyecto.modelo.Proveedor;

public interface ProveedorRepositorio extends JpaRepository<Proveedor, Integer> {
}