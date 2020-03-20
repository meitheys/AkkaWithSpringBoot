package com.akka.sprngakka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.akka.sprngakka.akka.PingAtor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SprngakkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprngakkaApplication.class, args);

		//Criando ATOR "SISTEMA"
		ActorSystem system = ActorSystem.create("Sistema");

		//Criando ATOR 'ping', com as propriedades da classe PingActor
		ActorRef pingActor = system.actorOf(PingAtor.props(), "ping");

		//Envia a msg
		pingActor.tell(new PingAtor.Initialize(), null);

		//Pega quando os métodos de print acabarem.
		system.getWhenTerminated();
	}
}