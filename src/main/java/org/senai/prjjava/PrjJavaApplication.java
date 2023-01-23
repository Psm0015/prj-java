package org.senai.prjjava;

// import org.senai.prjjava.services.EmailSenderService;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
// import org.springframework.context.event.EventListener;
// import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
@RestController
public class PrjJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrjJavaApplication.class, args);
	}
	
	// @GetMapping("/hello")
	// public String hello(@RequestParam(value = "nome", defaultValue = "World") String nome) {
	// return String.format("Hello %s!", nome);
	// }
	// @Bean
	// public PasswordEncoder getPasswordEncoder() {
	// 	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	// 	return encoder;
	// }

}
