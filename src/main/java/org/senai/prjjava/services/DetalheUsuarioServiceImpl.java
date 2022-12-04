package org.senai.prjjava.services;


import org.senai.prjjava.data.DetalheUsuarioData;
import org.senai.prjjava.entity.Usuario;
import org.senai.prjjava.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

// import net.bytebuddy.utility.RandomString;

import java.util.Optional;

// import javax.servlet.http.HttpServletRequest;

@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService {

    private final UsuarioRepository repository;
    private Usuario usuario;
    // private final EmailSenderService emailSenderService;

    public DetalheUsuarioServiceImpl(UsuarioRepository repository, EmailSenderService emailSenderService) {
        this.repository = repository;
        // this.emailSenderService = emailSenderService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repository.findByLogin(username);
        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException("Usuário [" + username + "] não encontrado");
        }
        return new DetalheUsuarioData(usuario);
    }

    // public void registrar(Usuario usuario, String siteURL){
    //     String randomCode = RandomString.make(64);
    //     usuario.setVerificationCode(randomCode);
    //     usuario.setEnabled(false);
            
    //     emailSenderService.emailconfirm(usuario, siteURL);
    // }

    // public String getSiteURL(HttpServletRequest request){
    //     String siteURL = request.getRequestURL().toString();
    //     return siteURL.replace(request.getServletPath(), "");
    // }
    public boolean isEnabled(){
        return usuario.isEnabled();
    }

}
