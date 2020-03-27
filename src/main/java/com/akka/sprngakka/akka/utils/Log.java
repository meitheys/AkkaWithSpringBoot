package com.akka.sprngakka.akka.utils;

import akka.actor.ActorSystem;
import com.typesafe.config.ConfigFactory;

public class Log{
    public static void main(String[] args) {
        ActorSystem.create("Log", ConfigFactory.load().getConfig("Log"));
    }
}
