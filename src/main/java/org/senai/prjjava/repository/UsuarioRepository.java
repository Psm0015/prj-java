package org.senai.prjjava.repository;

import java.util.Optional;

import org.senai.prjjava.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Optional<Usuario> findByLogin(String login);
    public Optional<Usuario> findByVerificationCode(String code);
}