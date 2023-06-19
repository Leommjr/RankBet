package br.com.rankbet.controller;

import br.com.rankbet.model.UserModel;
import br.com.rankbet.model.dto.UserDTO;
import br.com.rankbet.service.LoginService;
import br.com.rankbet.service.RoleService;
import br.com.rankbet.service.UserService;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.lang.reflect.InvocationTargetException;

@Named
@RequestScoped
@ManagedBean
public class RegisterBean {

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    private UserDTO userDTO;
    
    private UserService userService;

    @PostConstruct
    public void init() {
        userDTO= new UserDTO();
        userService = new UserService();
    }

    public String submit() throws InvocationTargetException, IllegalAccessException {
        System.out.println(userDTO);
        return (userService.registerUser(userDTO)) ? "sucess" : "error";
    }

}
