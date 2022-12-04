package org.senai.prjjava.services;

import org.senai.prjjava.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
// import org.springframework.stereotype.Service;

@Component
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void emailconfirm(Usuario user, String url){
        SimpleMailMessage mensagem=new SimpleMailMessage();
        mensagem.setFrom("pedromota.loja@gmail.com");
        mensagem.setTo(user.getEmail());
        mensagem.setText("Olá "+user.getNome()+
        ". Por favor clique no link para verificar seu registro:\n"
        +url+
        "\nObrigado!\n"+
        "Pedro Mota.");
        mensagem.setSubject("-NÃO RESPONDA-Por favor verifique seu Email para terminar o Cadastro!");

        mailSender.send(mensagem);

        // return "Email enviado com sucesso! para o endereço: "+endereco;
    }
}
