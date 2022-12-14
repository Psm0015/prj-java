package org.senai.prjjava.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;

// import java.util.ArrayList;
// import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String login;
    private String primeiro_nome;
    private String sobrenome;
    private String cep;
    private String endereco;
    private String numero;
    private String cidade;
    private String estado;
    private String password;
    @Column(name="verification_code", length = 64)
    private String verificationCode;
    private String carrinho;
    private String tk;
    private boolean enabled;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tab_user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role_id")
    private List<String> roles = new ArrayList<>();

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
