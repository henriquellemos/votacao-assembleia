package br.com.sicredi.votacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VotacaoAssembleiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotacaoAssembleiaApplication.class, args);
	}

}
