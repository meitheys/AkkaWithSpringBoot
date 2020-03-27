package com.akka.sprngakka.akka.ping;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.akka.sprngakka.akka.message.Mensagem;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class PingApp {

	public static void main(String[] args) {

			// Criação de um Actor System, container Akka.
			ActorSystem system = ActorSystem.create("RemotePing", ConfigFactory.load().getConfig("RemotePing"));

			// Criando o ator PING
			ActorRef actorRef = system.actorOf(Props.create(AtorPing.class));

			// Enviando a mensagem ao ator
			actorRef.tell(new Mensagem.Iniciar(), null);

			system.getWhenTerminated();
		}
}