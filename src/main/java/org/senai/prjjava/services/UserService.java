package org.senai.prjjava.services;

import java.util.ArrayList;
import java.util.List;

import org.senai.prjjava.entity.Usuario;
import org.senai.prjjava.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.bytebuddy.utility.RandomString;

@Service
public class UserService {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EmailSenderService email;
    public void createUser(Usuario user){
        String pass = user.getPassword();
        List<String> roles = new ArrayList<>();
        roles.add("User");
        user.setRoles(roles);
        String validcod = RandomString.make(64);
        user.setVerificationCode(validcod);
        user.setEnabled(false);
        
        String siteURL = "http://localhost:8080/noauth/validar?validarcod="+validcod;
        email.emailconfirm(user, siteURL);
        user.setPassword(encoder.encode(pass));
        repository.save(user);
    }
}
