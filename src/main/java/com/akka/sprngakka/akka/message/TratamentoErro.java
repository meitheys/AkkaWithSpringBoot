package com.akka.sprngakka.akka.message;

import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class TratamentoErro implements Serializable {

    public static class Erro implements Serializable {
        private final String mensagem;

        public Erro(String mensagem) {
            this.mensagem = mensagem;
        }
        public String getMensagem() {
            return mensagem;
        }
    }
}