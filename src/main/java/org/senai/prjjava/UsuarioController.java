package org.senai.prjjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//http://localhost:8080/api/usuario/add?nome=pedro&email=pedro@gmail.com
@Controller
@RequestMapping(path="/api/usuario")
public class UsuarioController {
    
    @Autowired
    UsuarioRepository uRepository;
    @PostMapping("/add")
    public @ResponseBody String addUsuario(
        @RequestParam String nome,
        @RequestParam String email){
        Usuario objU = new Usuario();
        objU.setNome(nome);
        objU.setEmail(email);
        uRepository.save(objU);
        return "Salvo";
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Usuario> buscarUsuarios(){
        return uRepository.findAll();
    }

}
