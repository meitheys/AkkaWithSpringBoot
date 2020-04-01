package com.akka.sprngakka.akka.message;

import org.springframework.stereotype.Service;

@Service
public class ServicoMensagemSpring {
    public void print(String msg) {
        System.out.println(msg);
    }
}