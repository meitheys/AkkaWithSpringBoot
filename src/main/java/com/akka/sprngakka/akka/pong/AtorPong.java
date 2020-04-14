package com.akka.sprngakka.akka.pong;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.akka.sprngakka.akka.message.Mensagem;
import com.akka.sprngakka.akka.spring.AtorGenerico;
import protobuf.PongMsg;

import java.util.Random;

@AtorGenerico
public class AtorPong extends UntypedAbstractActor{

    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

/*    -------------------------------------------------------------------------------------------

      Pegando Ator do Ping sem o remote
      private ActorRef atorPing = getContext().actorOf(Props.create(AtorPing.class), "AtorPing");

      -------------------------------------------------------------------------------------------    */

    //Remote
    //akka.<protocol>://<actor system name>@<hostname>:<port>/<actor path>
//    private ActorSelection atorPing = getContext().actorSelection("akka.tcp://RemotePing@127.0.0.1:6666/user/AtorPing");

    //Ao receber alguma ação, no caso, mensagem
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Mensagem.PingMsg) {

            Random random = new Random();

            int valor = random.nextInt(3);

            //Msg do Ping
            Mensagem.PingMsg pingMessage = (Mensagem.PingMsg) msg;

            //Imprimindo
            loggingAdapter.info("Recebendo: " + pingMessage.getMensagem() + ", do nivel: " + pingMessage.getNivel());

            //Pega quem enviou a mensagem anterior e re-envia "Pong"

            PongMsg pongMensagemProto = PongMsg.newBuilder().setMensagem("Pong").setNivel(2).build();

            getSender().tell(pongMensagemProto, getSelf());
        } else {
            //Se mensagem não for tratada
            unhandled(msg);
        }
    }
}