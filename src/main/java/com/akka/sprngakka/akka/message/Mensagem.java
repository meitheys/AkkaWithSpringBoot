package com.akka.sprngakka.akka.message;

public class Mensagem {

    public static class envia {

        private  String text;

        public envia(String text) {
            this.text = text;
        }

        public String recebe() {
            return text;
        }
    }

    public static class reenvia {
        private  String text;

        public reenvia(String text) {
            this.text = text;
        }

        public String recebe() {
            return text;
        }
    }
}