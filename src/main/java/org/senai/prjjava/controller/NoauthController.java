package org.senai.prjjava.controller;

import org.senai.prjjava.dtos.Login;
import org.senai.prjjava.dtos.Sessao;
import org.senai.prjjava.entity.Usuario;
import org.senai.prjjava.repository.UsuarioRepository;
import org.senai.prjjava.security.JWTCreator;
import org.senai.prjjava.security.JWTObject;
import org.senai.prjjava.security.SecurityConfig;
// import org.senai.prjjava.services.DetalheUsuarioServiceImpl;
import org.senai.prjjava.services.EmailSenderService;
import org.senai.prjjava.services.UserService;

import java.util.Date;
// import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;

import net.bytebuddy.utility.RandomString;


@RestController
@RequestMapping("/noauth")
@CrossOrigin("*")
public class NoauthController {

    private final UsuarioRepository urepository;
    private final PasswordEncoder encoder;
    private EmailSenderService email;
    @Autowired
    private UserService service;
    

    public NoauthController(UsuarioRepository repository, PasswordEncoder encoder, EmailSenderService email){
        this.urepository = repository;
        this.encoder = encoder;
        this.email = email;
        // this.service = service;
    }



    @PostMapping("/novo")
    public ResponseEntity<String> postUser(@RequestBody Usuario user){
        if(urepository.findByLogin(user.getLogin()) == null){
            service.createUser(user);
            return ResponseEntity.ok("Usuário Criado com Sucesso");
        }
        return ResponseEntity.status(406).body("Usuário já existe!");
    }

    // public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario, HttpServletRequest request){
    //     Usuario opusuario = urepository.findByLogin(usuario.getLogin());

    //     if(opusuario == null){
    //         usuario.setPassword(encoder.encode(usuario.getPassword()));
    //         String validcod = RandomString.make(64);
    //         usuario.setVerificationCode(validcod);
    //         usuario.setEnabled(false);
    //         // usuario.setRoles("Inativo");
    //         String siteURL = "http://localhost:8080/noauth/validar?validarcod="+validcod;
    //         email.emailconfirm(usuario, siteURL);
    //         // service.registrar(usuario, service.getSiteURL(request));
    //         return ResponseEntity.ok(urepository.save(usuario));
    //     }
    //     return ResponseEntity.badRequest().body(null);
        
    // }

    @GetMapping("/validar")
    public String validarEmail(@RequestParam String validarcod){
        Usuario usuariover = urepository.findByVerificationCode(validarcod);
        if(usuariover == null || usuariover.getVerificationCode() == null){
            return "<h1>Usuário não existe.</h1>";
        }else{
            usuariover.setVerificationCode(null);
            usuariover.setEnabled(true);
            // usuariover.setRoles("Usuário");
            ResponseEntity.ok(urepository.save(usuariover));
            return "<h1>Usuário verificado com sucesso!</h1>";
        }


    }
    @PostMapping("/login")
    public Sessao logar(@RequestBody Login login){
        Usuario user = urepository.findByLogin(login.getUsername());
        if(!(user==null)) {
            boolean passwordOk =  encoder.matches(login.getPassword(), user.getPassword());
            if (!passwordOk) {
                throw new RuntimeException("Senha inválida para o login: " + login.getUsername());
            }
            //Estamos enviando um objeto Sessão para retornar mais informações do usuário
            Sessao sessao = new Sessao();
            sessao.setLogin(user.getLogin());
            sessao.setNome(user.getPrimeiro_nome());
            sessao.setRoles(user.getRoles());
            
            JWTObject jwtObject = new JWTObject();
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
            jwtObject.setRoles(user.getRoles());
            sessao.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));
            user.setTk((sessao.getToken()).replace("Bearer ", ""));
            urepository.save(user);
            return sessao;
        }else {
            throw new RuntimeException("Erro ao tentar fazer login");
        }
    }
}
