package org.senai.prjjava.repository;

import java.util.Optional;

import org.senai.prjjava.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // public Optional<Usuario> findByLoginop(String login);
    public Usuario findByLogin(String login);
    public Usuario findByVerificationCode(String code);
    public Usuario findByTk(String tk);
}