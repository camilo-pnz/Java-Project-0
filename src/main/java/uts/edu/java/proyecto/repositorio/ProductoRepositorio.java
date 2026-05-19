package uts.edu.java.proyecto.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import uts.edu.java.proyecto.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {
}