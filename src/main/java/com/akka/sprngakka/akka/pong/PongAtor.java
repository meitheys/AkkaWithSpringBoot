package com.akka.sprngakka.akka.pong;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.akka.sprngakka.akka.akka.PingAtor;
import com.akka.sprngakka.akka.message.Mensagem;

public class PongAtor extends AbstractActor {

    //Logger para printar
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static class Initialize {
    }

    public static Props props() {
        return Props.create(PongAtor.class);
    }

    private ActorRef ping = getContext().actorOf(PingAtor.props(), "Ping");

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, this::printAndReturn)
                .build(
                );
    }

    private void printAndReturn(String s) {
        log.info("Mensagem: {}", s);
        log.info("Pong enviado");
        getSender().tell(new Mensagem.reenvia("Pong"), getSelf());

    }

}

