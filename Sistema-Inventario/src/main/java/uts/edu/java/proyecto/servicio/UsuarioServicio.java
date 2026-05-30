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
        if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2a$")) {
            String pass = usuario.getPassword();
            if (pass.length() < 8 ||
                !pass.matches(".*[A-Z].*") ||
                !pass.matches(".*[!@#$%^&*()\\-_=+{};:,<.>].*")) {
                throw new IllegalArgumentException(
                    "La contraseña debe tener mínimo 8 caracteres, una mayúscula y un carácter especial.");
            }
            usuario.setPassword(passwordEncoder.encode(pass));
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