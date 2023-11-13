package br.com.rankbet.template;

import br.com.rankbet.model.SubscriptionModel;
import br.com.rankbet.model.UserModel;

import java.time.LocalDateTime;

public class SubscriptonModelTemplate {

    public static SubscriptionModel validSubscriptionModel() {
        return new SubscriptionModel(1L, "Teste", LocalDateTime.now(),"Teste Upd",LocalDateTime.now(),34f,LocalDateTime.now(), 1, 1 );
    }



}
