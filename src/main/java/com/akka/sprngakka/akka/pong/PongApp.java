package com.akka.sprngakka.akka.pong;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class PongApp {

	public static void main(String[] args) {

		// Criação de um Actor System, container Akka.
		ActorSystem system = ActorSystem.create("RemotePong", ConfigFactory.load().getConfig("RemotePong"));

		//Criando ator PONG
		system.actorOf(Props.create(AtorPong.class), "AtorPong");

		system.getWhenTerminated();
	}
}