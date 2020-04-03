package com.akka.sprngakka.akka.supervisor;

import akka.actor.*;
import akka.japi.Function;
import akka.japi.pf.FI;
import com.akka.sprngakka.akka.pong.AtorPong;
import com.akka.sprngakka.akka.spring.AtorGenerico;
import scala.concurrent.duration.Duration;

@AtorGenerico
public class SupervisorPong extends AbstractActor {

    // REF = https://doc.akka.io/docs/akka/2.3/java/lambda-fault-tolerance.html#supervision-of-top-level-actors

    private ActorRef childActor = getContext().actorOf(Props.create(AtorPong.class), "AtorPong");

    private static SupervisorStrategy strategy = new OneForOneStrategy(5,
            Duration.create("10s"), new Function<Throwable, SupervisorStrategy.Directive>() {
        public SupervisorStrategy.Directive apply(Throwable t) {
            return OneForOneStrategy.resume();
        }
    });

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(new FI.UnitApply<Object>() {
            @Override
            public void apply(Object any) throws MinhaExcessao {
                childActor.forward(any, SupervisorPong.this.getContext());
            }
        }).build();
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}