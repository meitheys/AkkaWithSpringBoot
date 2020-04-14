package com.akka.sprngakka.akka.message;

import org.springframework.stereotype.Service;

@Service
public class Mensagem{

    public static class Iniciar {
    }

    public static class PongMsg{
        private final String mensagem;
        private final int nivel;

        public PongMsg(String mensagem, int nivel) {
            this.nivel = nivel;
            this.mensagem = mensagem;
        }

        public int getNivel() {
            return nivel;
        }

        public String getMensagem() {
            return mensagem;
        }
    }

    public static class PingMsg{
        private final String mensagem;
        private final int nivel;

        public PingMsg(String mensagem, int nivel) {
            this.nivel = nivel;
            this.mensagem = mensagem;
        }

        public int getNivel() {
            return nivel;
        }

        public String getMensagem() {
            return mensagem;
        }

        public static class Error {
            private NullPointerException mensagem;

            public Error(NullPointerException mensagem) {
                this.mensagem = mensagem;
            }

            public NullPointerException getMensagem() {
                return mensagem;
            }
        }
    }
}
