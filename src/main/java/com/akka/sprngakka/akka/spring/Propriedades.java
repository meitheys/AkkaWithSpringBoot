package com.akka.sprngakka.akka.spring;

import akka.actor.Actor;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Propriedades {

    public static Props create(ActorSystem system, Class<? extends Actor> actorBeanClass) {
        return Extender.getInstance().get(system).props(actorBeanClass);
    }

    public static Props create(ActorSystem system, Class<? extends Actor> actorBeanClass, Object... parameters) {
        return Extender.getInstance().get(system).props(actorBeanClass, parameters);
    }
}