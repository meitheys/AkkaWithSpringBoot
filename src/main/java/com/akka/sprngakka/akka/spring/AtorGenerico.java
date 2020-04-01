package com.akka.sprngakka.akka.spring;

import java.lang.annotation.*;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public @interface AtorGenerico { }
