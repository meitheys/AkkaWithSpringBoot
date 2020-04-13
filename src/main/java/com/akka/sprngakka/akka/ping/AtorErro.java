package com.akka.sprngakka.akka.ping;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.akka.sprngakka.akka.message.Mensagem;
import com.akka.sprngakka.akka.message.TratamentoErro;
import com.akka.sprngakka.akka.spring.AtorGenerico;

@AtorGenerico
public class AtorErro extends UntypedAbstractActor {

    LoggingAdapter LOG = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public void onReceive(Object mensagem) throws Throwable {
        if (mensagem instanceof Mensagem.PingMsg) {
            Mensagem.PingMsg actorPing = (Mensagem.PingMsg) mensagem;
            LOG.info("Recebendo erro: {} ", actorPing.getMensagem());
            getSender().tell(new TratamentoErro.Erro("Erro"), getSelf());
        }
    }
}