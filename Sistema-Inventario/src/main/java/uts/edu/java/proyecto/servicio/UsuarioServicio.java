package uts.edu.java.proyecto.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import uts.edu.java.proyecto.modelo.Usuario;
import uts.edu.java.proyecto.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicio implements IUsuarioServicio, UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listar() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public Usuario listarId(Integer id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario usuario) {
        // Solo encripta si la contraseña no está ya encriptada (usuario nuevo)
        if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public void delete(Integer id) {
        usuarioRepositorio.deleteById(id);
    }
    
    @Override
    public Usuario listarPorUsername(String username) {
        return usuarioRepositorio.findByUsername(username).orElse(null);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByUsername(username)
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