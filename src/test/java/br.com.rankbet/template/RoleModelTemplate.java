package br.com.rankbet.template;

import br.com.rankbet.model.RoleModel;
import br.com.rankbet.model.SubscriptionModel;

import java.time.LocalDateTime;

public class RoleModelTemplate {

    public static RoleModel validRoleModel() {
        return new RoleModel("TestName", "Descrição", LocalDateTime.now(),LocalDateTime.now(), 1, "Teste", "Teste");
    }

}
