package com.akka.sprngakka.akka.ping;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.akka.sprngakka.akka.message.Mensagem;

import java.io.Serializable;


public class AtorPing extends UntypedAbstractActor implements Serializable {
    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

    private ActorSelection atorPong = getContext().actorSelection("akka.tcp://RemotePong@127.0.0.1:5150/user/AtorPong");

    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Mensagem.Iniciar) {
            loggingAdapter.info("Iniciando o ping-pong");

            //Enviando para Pong
            atorPong.tell(new Mensagem.PingMsg("Ping"), getSelf());

            //Se mensagem for do Pong
        } else if (msg instanceof Mensagem.PongMsg) {

            //Pega MSG
            Mensagem.PongMsg atorPong = (Mensagem.PongMsg) msg;

            //Imprime a msg
            loggingAdapter.info("Recebendo: " + atorPong.getMensagem());
            loggingAdapter.info("Show: {}  ", atorPong.getMensagem());
        }
    }
}