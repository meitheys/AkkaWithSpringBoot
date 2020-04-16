package com.akka.sprngakka.akka.pong;

import akka.actor.ActorSystem;
import akka.actor.Props;
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
public class PongApp {

	@Autowired
	private ApplicationContext context;
	public static void main(String[] args) {
		System.getProperties().put( "server.port", 5150);
		SpringApplication.run(PongApp.class, args);
	}


	@PostConstruct
	void iniciarPong() {
		//Criando um Actor System
		ActorSystem system = ActorSystem.create("RemotePong", ConfigFactory.load().getConfig("RemotePong"));
		Extender.getInstance().get(system).initialize(context);
		//Criando ator PONG
//		system.actorOf(Propriedades.create(system, SupervisorPong.class), "AtorPong");
		system.actorOf(Props.create(AtorPong.class), "AtorPong");
		system.getWhenTerminated();
	}
}