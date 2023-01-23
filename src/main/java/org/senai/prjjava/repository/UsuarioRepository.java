package org.senai.prjjava.repository;

import org.senai.prjjava.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Usuario findByLogin(String login);
    public Usuario findByVerificationCode(String code);
    public Usuario findByTk(String tk);
}