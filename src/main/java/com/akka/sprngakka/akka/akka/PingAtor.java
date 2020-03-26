package com.akka.sprngakka.akka.akka;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.remote.RemoteActorRef;
import akka.remote.RemoteScope;
import com.akka.sprngakka.akka.message.Mensagem;
import com.akka.sprngakka.akka.pong.PongAtor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class PingAtor extends AbstractActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    //Criando PROPS, = configurações imutaveis do Ator
    public static Props props() {
        return Props.create(PingAtor.class);
    }

    public static class Initialize {
    }

    //Cria ator Pong
    private ActorRef pongActor = getContext().actorOf(PongAtor.props(), "Pong");

    ActorSelection selection =
            context().actorSelection("akka.tcp://Pong@127.0.0.1:5150");

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Mensagem.envia.class, this::inicio)
                .match(Mensagem.reenvia.class, this::print)
                .build();
    }

    private void print(Mensagem.reenvia mensagemRecebe) {
        log.info("Recebi: {}", mensagemRecebe.recebe());
    }

    private void inicio(Mensagem.envia mensagemEnvia) {
        // cria doc
        log.info(mensagemEnvia + " foi enviado");
        selection.tell(mensagemEnvia.recebe(), getSelf());
    }
}

