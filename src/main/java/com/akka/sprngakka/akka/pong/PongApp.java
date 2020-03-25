package com.akka.sprngakka.akka.pong;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.akka.sprngakka.akka.message.Mensagem;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class PongApp {

	public static void main(String[] args) {
		SpringApplication.run(PongApp.class, args);

		ActorSystem sistema = ActorSystem.create("SistemaPongSide");

		ActorRef pong = sistema.actorOf(PongAtor.props(), "Pong");

		pong.tell(new Mensagem.reenvia("pong"), null);
	}
}