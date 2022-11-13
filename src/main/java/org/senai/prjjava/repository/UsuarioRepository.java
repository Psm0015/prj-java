package org.senai.prjjava.repository;

import org.senai.prjjava.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{

    
}