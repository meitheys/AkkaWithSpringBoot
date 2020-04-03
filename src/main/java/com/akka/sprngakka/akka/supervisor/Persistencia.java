package com.akka.sprngakka.akka.supervisor;

import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.actor.TypedActor;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

public class Persistencia implements TypedActor.Supervisor {

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(-1, Duration.Inf(), new Function<Throwable, SupervisorStrategy.Directive>() {
            public SupervisorStrategy.Directive apply(Throwable t) throws Exception {
                return OneForOneStrategy.resume();
            }
        });
    }
}
