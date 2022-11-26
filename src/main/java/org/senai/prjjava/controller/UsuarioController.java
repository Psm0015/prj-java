package org.senai.prjjava.controller;

import org.senai.prjjava.entity.Usuario;
import org.senai.prjjava.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioRepository urepository;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository repository, PasswordEncoder encoder){
        this.urepository = repository;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> listarTodos(){
        return ResponseEntity.ok(urepository.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        return ResponseEntity.ok(urepository.save(usuario));
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
