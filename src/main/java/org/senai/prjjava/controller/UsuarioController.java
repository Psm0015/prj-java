package org.senai.prjjava.controller;

import org.senai.prjjava.entity.Usuario;
import org.senai.prjjava.repository.UsuarioRepository;
// import org.senai.prjjava.services.DetalheUsuarioServiceImpl;
import org.senai.prjjava.services.EmailSenderService;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.bytebuddy.utility.RandomString;



@RestController
@RequestMapping("/api/usuario")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioRepository urepository;
    private final PasswordEncoder encoder;
    private EmailSenderService email;
    

    public UsuarioController(UsuarioRepository repository, PasswordEncoder encoder, EmailSenderService email){
        this.urepository = repository;
        this.encoder = encoder;
        this.email = email;
        // this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> listarTodos(){
        return ResponseEntity.ok(urepository.findAll());
        
    }

    @PostMapping("/novo")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario, HttpServletRequest request){
        Optional<Usuario> opusuario = urepository.findByLogin(usuario.getLogin());

        if(opusuario.isEmpty()){
            usuario.setPassword(encoder.encode(usuario.getPassword()));
            String validcod = RandomString.make(64);
            usuario.setVerificationCode(validcod);
            usuario.setEnabled(false);
            String siteURL = "http://localhost:8080/api/usuario/validar?validarcod="+validcod;
            email.emailconfirm(usuario, siteURL);
            // service.registrar(usuario, service.getSiteURL(request));
            return ResponseEntity.ok(urepository.save(usuario));
        }
        return ResponseEntity.badRequest().body(null);
        
    }

    @GetMapping("/validar")
    public String validarEmail(@RequestParam String validarcod){
        Optional<Usuario> usuariover = urepository.findByVerificationCode(validarcod);
        if(usuariover == null || usuariover.get().getVerificationCode() == null){
            return "<h1>Usuário não existe.</h1>";
        }else{
            usuariover.get().setVerificationCode(null);
            usuariover.get().setEnabled(true);
            ResponseEntity.ok(urepository.save(usuariover.get()));
            return "<h1>Usuário verificado com sucesso!</h1>";
        }


    }

    @PutMapping("/editar")
    public ResponseEntity<Usuario> editar(@RequestBody Usuario usuario){
        Optional<Usuario> optUsuario = urepository.findById(usuario.getId());
        if (optUsuario.isEmpty()){
            return ResponseEntity.badRequest().body(null);
        }
        Usuario usuario2 = optUsuario.get();
        boolean valid = encoder.matches(usuario.getPassword(), usuario2.getPassword());

        if(valid){
            usuario.setPassword(encoder.encode(usuario.getPassword()));
            return ResponseEntity.ok(urepository.save(usuario));
        }
        return ResponseEntity.badRequest().body(null);
    }
    @GetMapping("/login")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String login,@RequestParam String password){

        Optional<Usuario> optUsuario = urepository.findByLogin(login);
        if (optUsuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        Usuario usuario =optUsuario.get();
        boolean valid = encoder.matches(password, usuario.getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(valid);
    }
    @GetMapping("/{id}")
    public @ResponseBody Optional<Usuario> buscarUsuario(@PathVariable Integer id){
        return urepository.findById(id);
    }
    @DeleteMapping("/{id}")
    public @ResponseBody String apagar(@PathVariable Integer id){
        urepository.deleteById(id);
        return "Usuario deletado com Sucesso!";
    }
}
