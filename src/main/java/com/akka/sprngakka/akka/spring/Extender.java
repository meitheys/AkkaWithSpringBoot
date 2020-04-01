package com.akka.sprngakka.akka.spring;

import akka.actor.*;
import org.springframework.context.ApplicationContext;

public class Extender extends AbstractExtensionId<Extender.SpringExt> {

    private static Extender instance = new Extender();

    @Override
    public SpringExt createExtension(ExtendedActorSystem system) {
        return new SpringExt();
    }

    public static Extender getInstance() {
        return instance;
    }

    public static class SpringExt implements Extension {

        private static ApplicationContext applicationContext;

        public void initialize(ApplicationContext applicationContext) {
            SpringExt.applicationContext = applicationContext;
        }

        Props props(Class<? extends Actor> actorBeanClass) {
            return Props.create(Iniciador.class, applicationContext, actorBeanClass);
        }

        Props props(Class<? extends Actor> actorBeanClass, Object... parameters) {
            return Props.create(Iniciador.class, applicationContext, actorBeanClass, parameters);
        }
    }
}