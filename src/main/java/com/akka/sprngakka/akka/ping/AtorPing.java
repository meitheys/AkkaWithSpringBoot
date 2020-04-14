package com.akka.sprngakka.akka.ping;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.akka.sprngakka.akka.spring.AtorGenerico;
import protobuf.PingMsg;
import protobuf.PongMsg;
import scala.concurrent.duration.Duration;

@AtorGenerico
public class AtorPing extends UntypedAbstractActor {

    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

    //Remote
    private ActorSelection atorPong = getContext().actorSelection("akka.tcp://RemotePong@127.0.0.1:5150/user/AtorPong");

    //Ao Receber
    public void onReceive(Object msg) throws Exception {

        //Se mensagem for do tipo iniciar
        if (msg instanceof PongMsg) {

            //Imprime a msg
            loggingAdapter.info("Recebendo: " + msg);

        }else {
            loggingAdapter.info("Iniciando o ping-pong");

            PingMsg pingMensagemProto = PingMsg.newBuilder().setMensagem("Ping").setNivel(2).build();

            //Enviando para Pong
            atorPong.tell(pingMensagemProto, getSelf());

            //Se mensagem for do Pong

        }
    }
}