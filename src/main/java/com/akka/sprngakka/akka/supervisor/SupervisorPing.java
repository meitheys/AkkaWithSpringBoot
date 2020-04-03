package com.akka.sprngakka.akka.supervisor;


import akka.actor.*;
import akka.japi.Function;
import akka.japi.pf.FI;
import com.akka.sprngakka.akka.ping.AtorPing;
import com.akka.sprngakka.akka.spring.AtorGenerico;
import scala.concurrent.duration.Duration;

@AtorGenerico
public class SupervisorPing extends AbstractActor {

    // REF = https://doc.akka.io/docs/akka/2.3/java/lambda-fault-tolerance.html#supervision-of-top-level-actors

    //Referencia
    final ActorRef actorRef = getContext().actorOf(Props.create(AtorPing.class), "AtorPing");

    //Persistencia do supervisor.
    private static SupervisorStrategy strategy = new OneForOneStrategy(5,
            Duration.create("10s"), new Function<Throwable, SupervisorStrategy.Directive>() {
        public SupervisorStrategy.Directive apply(Throwable t) {

            //Continua outras atividades mesmo se uma falhar
            return OneForOneStrategy.resume();
        }
    });

    //Padrão de Override
    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(new FI.UnitApply<Object>() {
            @Override
            public void apply(Object any) throws MinhaExcessao {
                actorRef.forward(any, SupervisorPing.this.getContext());
            }
        }).build();
    }

    //Padrão de Override
    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}