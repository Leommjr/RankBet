package br.com.rankbet.template;

import br.com.rankbet.model.UserModel;
import br.com.rankbet.utils.PasswordUtil;

import java.time.LocalDateTime;

public class UserModelTemplate {

    public static UserModel validUserModel() {
        return new UserModel(1l,"Teste", "UsuarioTest", "teste@email.com", "tester",
                PasswordUtil.generateMD5("xxxxxx"), LocalDateTime.now(),LocalDateTime.now(),1L);
    }



}
