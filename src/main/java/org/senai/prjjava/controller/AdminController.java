package org.senai.prjjava.controller;

import org.senai.prjjava.entity.Produto;
import org.senai.prjjava.entity.Usuario;
import org.senai.prjjava.repository.ProdutoRepository;
import org.senai.prjjava.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adm")
@CrossOrigin("*")
public class AdminController {
    
    private final UsuarioRepository urepository;
    private final PasswordEncoder encoder;
    private ProdutoRepository pRepository;
    

    public AdminController(UsuarioRepository repository, PasswordEncoder encoder, ProdutoRepository pRepository){
        this.urepository = repository;
        this.encoder = encoder;
        this.pRepository = pRepository;
        Usuario useradmin = new Usuario();
        List<String> roles = new ArrayList<>();
        roles.add("Admin");
        useradmin.setRoles(roles);
        useradmin.setLogin("admin");
        useradmin.setEnabled(true);
        useradmin.setPassword(encoder.encode("admin123"));
        if (urepository.findByLogin("admin") == null){
            urepository.save(useradmin);
        }
    }

    @GetMapping("/listarusuarios")
    public ResponseEntity<List<Usuario>> listarTodos(){
        return ResponseEntity.ok(urepository.findAll());
        
    }
    @PutMapping("/setadm/{id}")
    public void upadm(@PathVariable Integer id){
        Optional<Usuario> usu = urepository.findById(id);
        List<String> roles = new ArrayList<>();
        roles.add("Admin");
        usu.get().setRoles(roles);
        Usuario useradm = usu.get();
        urepository.save(useradm);
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
    @GetMapping("/buscausuario/{id}")
    public @ResponseBody Optional<Usuario> buscarUsuario(@PathVariable Integer id){
        return urepository.findById(id);
    }
    @DeleteMapping("/deletarusuario/{id}")
    public @ResponseBody String apagarusuario(@PathVariable Integer id){
        urepository.deleteById(id);
        return "Usuario deletado com Sucesso!";
    }

    @PostMapping("/addprd")
    public @ResponseBody Integer addProduto(@RequestBody Produto objP){
        pRepository.save(objP);
        return objP.getId();
    }

    @PutMapping("/atualizarprd")
    public @ResponseBody Produto atualizarprd(@RequestBody Produto objP){
        pRepository.save(objP);
        return objP;
    }

    @DeleteMapping("/deletarprd/{id}")
    public @ResponseBody String apagarprd(@PathVariable Integer id){
        pRepository.deleteById(id);
        return "Produto deletado com Sucesso!";
    }
}
