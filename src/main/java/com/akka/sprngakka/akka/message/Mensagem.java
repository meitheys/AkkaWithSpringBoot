package com.akka.sprngakka.akka.message;

import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class Mensagem{

    public static class Iniciar {
    }

    public static class PongMsg{
        private final String mensagem;

        public PongMsg(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getMensagem() {
            return mensagem;
        }
    }

    public static class PingMsg{
        private final String mensagem;

        public PingMsg(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getMensagem() {
            return mensagem;
        }
    }
}
