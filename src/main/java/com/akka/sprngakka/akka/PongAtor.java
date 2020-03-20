package com.akka.sprngakka.akka;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PongAtor extends UntypedAbstractActor {

    //Logger para printar
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    //Propriedades, nativas do Akka, as mesmas são imutaveis
    public static Props props() {
        return Props.create(PongAtor.class);
    }

    public static class PongMessage {

        //Mensagem que Pong conterá
        private final String textoMSG;

        public PongMessage(String text) {
            this.textoMSG = text;
        }

        public String getTextoMSG() {
            return textoMSG;
        }
    }

    //onReceive já é um método do UntypedAbstractActor, que faz literalmente o que o nome do método fala.
    public void onReceive(Object message) throws Exception {

        //Se for do tipo do método MensagemDoPing na class PingAtor
        if (message instanceof PingAtor.MensagemDoPing) {

            //Popula o ping
            PingAtor.MensagemDoPing ping = (PingAtor.MensagemDoPing) message;

            //Printa 'Ping'
            log.info("Mensagem do Pong: {}", ping.getText());

            //Envia 'Pong' para o PingAtor
            getSender().tell(new PongMessage("Pong"), getSelf());

            //Se mensagem não for do método MensagemDoPing, termina com erro.
        } else {
            unhandled(message);
        }
    }
}

