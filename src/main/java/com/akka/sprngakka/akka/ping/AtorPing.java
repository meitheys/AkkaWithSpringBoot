package com.akka.sprngakka.akka.ping;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.stream.impl.FanIn;
import com.akka.sprngakka.akka.message.Mensagem;
import com.akka.sprngakka.akka.message.TratamentoErro;
import com.akka.sprngakka.akka.pong.AtorPong;
import com.akka.sprngakka.akka.spring.AtorGenerico;
import scala.concurrent.duration.Duration;

@AtorGenerico
public class AtorPing extends UntypedAbstractActor {

    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

    public SupervisorStrategy strategy =
            new OneForOneStrategy(20, Duration.create("10 seconds"),
                    e -> {
                        if (e instanceof NullPointerException) {
                            loggingAdapter.info("Erro: " + e.getMessage(), e);
                            return SupervisorStrategy.restart();
                        } else {
                            loggingAdapter.info("Escalando");
                            return SupervisorStrategy.escalate();
                        }
                    });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    //Remote
    private ActorSelection atorPong = getContext().actorSelection("akka.tcp://RemotePong@127.0.0.1:5150/user/AtorPong");

    private ActorRef atorErro =  getContext().actorOf(Props.create(AtorErro.class), "AtorErro");

    //Ao Receber
    public void onReceive(Object msg) throws Exception {
        //Se mensagem for do tipo iniciar
        if (msg instanceof Mensagem.Iniciar) {
            loggingAdapter.info("Iniciando o ping-pong");

            //Enviando para Pong
            atorPong.tell(new Mensagem.PingMsg("Ping"), getSelf());

            //Se mensagem for do Pong
        } else if (msg instanceof Mensagem.PongMsg) {

            //Pega MSG
            Mensagem.PongMsg pingMessage = (Mensagem.PongMsg) msg;

            //Imprime a msg
            loggingAdapter.info("Recebendo: " + pingMessage.getMensagem());

        } else if (msg instanceof TratamentoErro.Erro) {

            //Imprime a msg
            loggingAdapter.info("Tratando Erro");

            atorPong.tell(new Mensagem.PingMsg("Erro durante troca de mensagens"), getSelf());

        }else{
            atorErro.tell(new TratamentoErro.Erro("Erro"), getSelf());
            this.postRestart(null);
            unhandled(msg);
        }
    }
}