package com.akka.sprngakka.akka.supervisor;

import akka.actor.ActorSystem;

import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategyConfigurator;
import akka.pattern.BackoffOptions;
import akka.pattern.BackoffOptionsImpl;
import akka.pattern.BackoffOpts;
import akka.pattern.BackoffSupervisor;
import com.akka.sprngakka.akka.pong.AtorPong;
import com.akka.sprngakka.akka.pong.PongApp;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import scala.collection.immutable.Nil;

import java.time.Duration;
import java.util.List;

@EnableAutoConfiguration
@SpringBootApplication
public class Supervisor {

    public static void main(String[] args) {

        ActorSystem supervisor = ActorSystem.create("Supervisor");

        supervisor.getWhenTerminated();


        final Props childProps = Props.create(AtorPong.class);

        final Props supervisorProps =
                BackoffSupervisor.props(
                        BackoffOpts.onStop(
                                childProps,
                                "myEcho",
                                Duration.ofSeconds(3),
                                Duration.ofSeconds(30),
                                0.2)); // adds 20% "noise" to vary the intervals slightly

        supervisor.actorOf(supervisorProps, "echoSupervisor");

    }
}













