package com.akka.sprngakka.akka.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import com.akka.sprngakka.akka.message.Mensagem;
import com.akka.sprngakka.akka.pong.PongAtor;
import org.springframework.beans.factory.annotation.Value;

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
        pongActor.tell(mensagemEnvia.recebe(), getSelf());
    }
}

