package com.akka.sprngakka.akka.message;

import org.springframework.stereotype.Service;

@Service
public class TratamentoErro{

    public static class Erro{
        private final String mensagem;

        public Erro(String mensagem) {
            this.mensagem = mensagem;
        }
        public String getMensagem() {
            return mensagem;
        }
    }
}