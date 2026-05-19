package uts.edu.java.proyecto.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;

import uts.edu.java.proyecto.modelo.Usuario;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
}