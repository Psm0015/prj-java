package org.senai.prjjava.controller;

import org.senai.prjjava.entity.Produto;
import org.senai.prjjava.entity.Usuario;
import org.senai.prjjava.repository.ProdutoRepository;
import org.senai.prjjava.repository.UsuarioRepository;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioRepository urepository;
    private final PasswordEncoder encoder;
    private ProdutoRepository pRepository;
    

    public UsuarioController(ProdutoRepository pRepository, UsuarioRepository repository, PasswordEncoder encoder){
        this.urepository = repository;
        this.encoder = encoder;
        this.pRepository = pRepository;
    }


    //http:///api/produto/2 -  busca usuario unico usando a variável de path(caminho)
    @GetMapping("/buscarprd/{id}")
    public @ResponseBody Optional<Produto> buscarProduto(@PathVariable Integer id){
        return pRepository.findById(id);
    }

    @GetMapping("/identificar/{tk}")
    public Usuario buscarUsuario(@PathVariable String tk){
        return urepository.findByTk(tk);
    }
    @PutMapping("/editar/{tk}")
    public ResponseEntity<Usuario> editar(@PathVariable String tk, @RequestBody Usuario usuario){
        Optional<Usuario> optUsuario = urepository.findById(urepository.findByTk(tk).getId());
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
    @GetMapping("/buscarprds")
    public @ResponseBody Iterable<Produto> buscarProdutos(){
        return pRepository.findAll();
    }
    
}
