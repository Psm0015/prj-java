package org.senai.prjjava.controller;

import org.senai.prjjava.dtos.Login;
import org.senai.prjjava.dtos.Sessao;
import org.senai.prjjava.entity.Produto;
import org.senai.prjjava.entity.Usuario;
import org.senai.prjjava.repository.ProdutoRepository;
import org.senai.prjjava.repository.UsuarioRepository;
import org.senai.prjjava.security.JWTCreator;
import org.senai.prjjava.security.JWTObject;
import org.senai.prjjava.security.SecurityConfig;
import org.senai.prjjava.services.UserService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/noauth")
@CrossOrigin("*")
public class NoauthController {

    private final UsuarioRepository urepository;
    private final PasswordEncoder encoder;
    private ProdutoRepository pRepository;
    @Autowired
    private UserService service;
    

    public NoauthController(UsuarioRepository repository, PasswordEncoder encoder, ProdutoRepository pRepository){
        this.urepository = repository;
        this.encoder = encoder;
        this.pRepository = pRepository;
    }



    @PostMapping("/novo")
    public ResponseEntity<String> postUser(@RequestBody Usuario user){
        if(urepository.findByLogin(user.getLogin()) == null){
            service.createUser(user);
            return ResponseEntity.ok("Usuário Criado com Sucesso");
        }
        return ResponseEntity.status(406).body("Usuário já existe!");
    }

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
    @GetMapping("/buscarprds")
    public @ResponseBody Iterable<Produto> buscarProdutos(){
        return pRepository.findAll();
    }
}
