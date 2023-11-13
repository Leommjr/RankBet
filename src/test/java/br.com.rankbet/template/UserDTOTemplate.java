package br.com.rankbet.template;

import br.com.rankbet.model.dto.UserDTO;

public class UserDTOTemplate {

    public static UserDTO validUserDTO() {
        return new UserDTO("Teste", "UsuarioTest", "teste@email.com", "tester",
                "xxxxxx", "xxxxxx");
    }



}
