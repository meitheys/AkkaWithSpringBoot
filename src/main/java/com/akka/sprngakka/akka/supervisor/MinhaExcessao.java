package com.akka.sprngakka.akka.supervisor;

public class MinhaExcessao extends Exception {

    private static String message = "MyException";

    public MinhaExcessao(String message) {
        this.message = message;
    }

    public MinhaExcessao() {
        super(message);
    }
}