package uts.edu.java.proyecto.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import uts.edu.java.proyecto.modelo.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer> {
}