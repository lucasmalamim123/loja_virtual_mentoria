package jdev.mentoria.lojaVirtual.service;

import jdev.mentoria.lojaVirtual.models.Usuario;
import jdev.mentoria.lojaVirtual.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findUserByLogin(userName); /* recebe login para consulta */

        if(usuario == null){
            throw new UsernameNotFoundException("Usuário não foi encotrado");
        }
        return new User(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
    }
}
