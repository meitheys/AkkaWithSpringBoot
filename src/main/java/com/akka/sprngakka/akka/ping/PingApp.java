package com.akka.sprngakka.akka.ping;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.akka.sprngakka.akka.message.Mensagem;
import com.akka.sprngakka.akka.spring.Extender;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

@EnableAutoConfiguration
@SpringBootApplication
public class PingApp {

	@Autowired
	private ApplicationContext context;
	public static void main(String[] args) {
		System.getProperties().put( "server.port", 6666);
		SpringApplication.run(PingApp.class, args);
	}

	@PostConstruct
	void iniciarPing() {
			// Criação de um Actor System, container Akka.
			ActorSystem system = ActorSystem.create("RemotePing", ConfigFactory.load().getConfig("RemotePing"));
			Extender.getInstance().get(system).initialize(context);
			// Criando o ator PING
			ActorRef actorRef = system.actorOf(Props.create(AtorPing.class));

			// Enviando a mensagem ao ator
			actorRef.tell(new Mensagem.Iniciar(), null);

			system.getWhenTerminated();
		}
}