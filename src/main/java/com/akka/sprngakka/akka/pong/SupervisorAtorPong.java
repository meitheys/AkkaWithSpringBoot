package com.akka.sprngakka.akka.pong;

import akka.actor.*;
import akka.japi.Function;
import akka.japi.pf.FI;
import com.akka.sprngakka.akka.message.Mensagem;
import com.akka.sprngakka.akka.spring.AtorGenerico;

import scala.concurrent.duration.Duration;

@AtorGenerico
public class SupervisorAtorPong extends AbstractActor {

    private ActorRef pongPrimarioS = getContext().actorOf(Props.create(AtorPong.class), "pongPrimarioS");
    private ActorRef pongSecundarioS = getContext().actorOf(Props.create(AtorPong.class), "pongSecundarioS");
    private ActorRef pongTercearioS = getContext().actorOf(Props.create(AtorPong.class), "pongTercearioS");

    private static SupervisorStrategy strategy = new OneForOneStrategy(3,
            Duration.create("10 seconds"), new Function<Throwable, SupervisorStrategy.Directive>() {
        public SupervisorStrategy.Directive apply(Throwable t) {
            return OneForOneStrategy.resume();
        }
    });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder().matchAny(new FI.UnitApply<Object>() {
            @Override
            public void apply(Object any) throws Exception {
                if (any instanceof Mensagem.PingMsg) {
                    Mensagem.PingMsg msg = (Mensagem.PingMsg) any;
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

                    if (mensagem.equalsIgnoreCase("Ping1")) {
                        pongPrimarioS.forward(any, SupervisorAtorPong.this.getContext());
                    } else if (mensagem.equalsIgnoreCase("Ping2")) {
                        pongSecundarioS.forward(any, SupervisorAtorPong.this.getContext());
                    } else if (mensagem.equalsIgnoreCase("Ping3")) {
                        pongTercearioS.forward(any, SupervisorAtorPong.this.getContext());
                    }
                }
            }
        }).build();
    }
}