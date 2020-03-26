package com.akka.sprngakka.akka.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.akka.sprngakka.akka.message.Mensagem;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class SprngakkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprngakkaApplication.class, args);

		//Criando ATOR "SISTEMA"
		ActorSystem system = ActorSystem.create("SistemaPing");

		//Criando ATOR 'ping', com as propriedades da classe PingActor
		ActorRef pingActor = system.actorOf(PingAtor.props(), "ping") ;

		//Envia a msg
		pingActor.tell(new Mensagem.envia("Ping"), null);

	}
}