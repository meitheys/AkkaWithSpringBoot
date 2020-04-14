package com.akka.sprngakka.akka.ping;

import akka.actor.*;
import akka.japi.Function;
import akka.japi.pf.FI;
import com.akka.sprngakka.akka.message.Mensagem;
import com.akka.sprngakka.akka.pong.AtorPong;
import com.akka.sprngakka.akka.spring.AtorGenerico;

import scala.concurrent.duration.Duration;

@AtorGenerico
public class SupervisorAtorPing extends AbstractActor{

    //Três opções de nivel para a mensagem retornar em 'X' ator

    final ActorRef pongPrimarioS = getContext().actorOf(Props.create(AtorPing.class), "pongPrimarioS");
    final ActorRef pongSecundarioS = getContext().actorOf(Props.create(AtorPing.class), "pongSecundarioS");
    final ActorRef pongTercearioS = getContext().actorOf(Props.create(AtorPing.class), "pongTercearioS");

    //Life-Cicle

    private static SupervisorStrategy strategy = new OneForOneStrategy(3,
            Duration.create("10 second"), new Function<Throwable, SupervisorStrategy.Directive>() {
        public SupervisorStrategy.Directive apply(Throwable t) {
            return OneForOneStrategy.resume();
        }
    });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(new FI.UnitApply<Object>() {
            @Override
            public void apply(Object any) throws Exception {
                if (any instanceof Mensagem.Iniciar) {

                    //Remote Pong
                    ActorSelection atorPong = getContext().actorSelection("akka.tcp://RemotePong@127.0.0.1:5150/user/AtorPong");

                    //Mensagem
                    atorPong.tell(new Mensagem.PingMsg("Ping", 2), getSelf());
                }
                if (any instanceof Mensagem.PongMsg) {
                    Mensagem.PongMsg msg = (Mensagem.PongMsg) any;
                    String mensagem = msg.getMensagem() + msg.getNivel();

                    /*

                    Explicação Forward VS Tell

                    ---|| Tell ||---

                    A tells message M to B.
                    B tells that message to C.
                    C thinks the sender() of message M is B.


                    A "tells" mensagem 'M' para 'B'
                    B "tells" a mesma para 'C'
                    C acha que o sender da mensagem 'M' foi 'B'

                    A (M) -> B | B (M) -> C, C assume que B enviou M

                    ---|| Forward ||---

                    A "tells" mensagem 'M' para 'B'
                    B "forwards" a mesma para 'C'
                    C acha que sender da mensagem 'M' é 'A'

                    A (M) -> B | B (M) -> C, C assume que A enviou M, B não a enviou, só repassou a mensagem.

                    */

                    if (mensagem.equalsIgnoreCase("Pong1")) {
                        pongPrimarioS.forward(any, SupervisorAtorPing.this.getContext());
                    } else if (mensagem.equalsIgnoreCase("Pong2")) {
                        pongSecundarioS.forward(any, SupervisorAtorPing.this.getContext());
                    } else if (mensagem.equalsIgnoreCase("Pong3")) {
                        pongTercearioS.forward(any, SupervisorAtorPing.this.getContext());
                    }
                }
            }
        }).build();
    }
}