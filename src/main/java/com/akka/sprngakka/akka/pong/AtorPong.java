package com.akka.sprngakka.akka.pong;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.akka.sprngakka.akka.message.Mensagem;
import com.akka.sprngakka.akka.ping.AtorPing;

import java.io.Serializable;

public class AtorPong extends UntypedAbstractActor implements Serializable {

    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

    private int contador = 0;
    private ActorRef atorPing = getContext().actorOf(Props.create(AtorPing.class), "AtorPing");


    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Mensagem.PingMsg) {
            //pegar a mensagem do AtorPing
            Mensagem.PingMsg atorPing = (Mensagem.PingMsg) msg;
            //mostra a mensagem enviado do AtorPing para o AtorPong no console
            loggingAdapter.info("Recebendo: " + atorPing.getMensagem());
            loggingAdapter.info("Enviando: " + atorPing.getMensagem());
            //inforna a mensagem que o AtorPong esta passando
            contador += 1;
            if (contador == 3) {
                getContext().system().terminate();
            } else {
                getSender().tell(new Mensagem.PongMsg("Pong"), getSelf());
            }
        } else {
            unhandled(msg);
        }
    }
}