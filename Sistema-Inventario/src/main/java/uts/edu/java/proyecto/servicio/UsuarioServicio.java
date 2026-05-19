package uts.edu.java.proyecto.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

import uts.edu.java.proyecto.modelo.Usuario;
import uts.edu.java.proyecto.repositorio.UsuarioRepositorio;

@Service
//Agregamos IUsuarioServicio a la lista de implementaciones
public class UsuarioServicio implements IUsuarioServicio, UserDetailsService {

 @Autowired
 private UsuarioRepositorio usuarioRepositorio;

 // --- MÉTODOS DE IUsuarioServicio (Faltaban en tu clase) ---

 @Override
 public List<uts.edu.java.proyecto.modelo.Usuario> listar() {
     return (List<uts.edu.java.proyecto.modelo.Usuario>) usuarioRepositorio.findAll();
 }

 @Override
 public uts.edu.java.proyecto.modelo.Usuario listarId(Integer id) {
     return usuarioRepositorio.findById(id).orElse(null);
 }

 @Override
 public uts.edu.java.proyecto.modelo.Usuario save(uts.edu.java.proyecto.modelo.Usuario usuario) {
     return usuarioRepositorio.save(usuario);
 }

 @Override
 public void delete(Integer id) {
     usuarioRepositorio.deleteById(id);
 }

 // --- MÉTODO DE UserDetailsService (El que ya tenías corregido) ---

 @Override
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     uts.edu.java.proyecto.modelo.Usuario usuario = usuarioRepositorio.findByUsername(username)
             .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

     SimpleGrantedAuthority authority = 
             new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name());

     return new org.springframework.security.core.userdetails.User(
             usuario.getUsername(),
             usuario.getPassword(),
             usuario.isActivo(),
             true,
             true,
             true,
             List.of(authority)
     );
 }
}