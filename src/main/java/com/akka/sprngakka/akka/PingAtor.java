package com.akka.sprngakka.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PingAtor extends UntypedAbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    //Criando PROPS, = configurações imutaveis do Ator
    public static Props props() {
        return Props.create(PingAtor.class);
    }

    public static class Initialize {
    }

    public static class MensagemDoPing {
        private final String text;

        public MensagemDoPing(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    private int counter = 0;
    private ActorRef pongActor = getContext().actorOf(PongAtor.props(), "Pong");

    public void onReceive(Object mensagemAPrintar) throws Exception {

        //Se mensagem for do tipo Initialize, começa o jogo
        if (mensagemAPrintar instanceof Initialize) {

            //Logger de Inicio do jogo
            log.info("Iniciando o Ping-Pong");
            pongActor.tell(new MensagemDoPing("Ping"), getSelf());

            //Se for do tipo Pong, PongMessage
        } else if (mensagemAPrintar instanceof PongAtor.PongMessage) {
            PongAtor.PongMessage pong = (PongAtor.PongMessage) mensagemAPrintar;

            //Logger inicial
            log.info("Mensagem do Ping: {}", pong.getTextoMSG());
            counter += 1;

            //Se for 3, termina
            if (counter == 3) {
                getContext().system().terminate();

                //Se não for 3, continua
            } else {

                //Envia 'Ping' para o Pong
                getSender().tell(new MensagemDoPing("Ping"), getSelf());
            }

            //Se  mensagemAPrintar não for do tipo Initialize, termina com erro.
        } else {
            unhandled(mensagemAPrintar);
        }
    }
}

