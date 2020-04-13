package com.akka.sprngakka.akka.message;

import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class Mensagem implements Serializable {

    public static class Iniciar {
    }

    public static class PongMsg implements Serializable {
        private final String mensagem;

        public PongMsg(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getMensagem() {
            return mensagem;
        }
    }

    public static class PingMsg implements Serializable {

        private final String mensagem;

        public PingMsg(String mensagem) {
            this.mensagem = mensagem;
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
