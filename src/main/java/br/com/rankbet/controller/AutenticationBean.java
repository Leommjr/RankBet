package br.com.rankbet.controller;

import br.com.rankbet.enums.AccountType;
import br.com.rankbet.model.SubscriptionModel;
import br.com.rankbet.model.UserModel;
import br.com.rankbet.model.dto.UserDTO;
import br.com.rankbet.service.LoginService;
import br.com.rankbet.service.RoleService;
import br.com.rankbet.service.SubscriptionService;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.util.Optional;

@Named
@ViewScoped
@ManagedBean
public class AutenticationBean {

    private UserDTO userDTO;

    private LoginService loginService;

    private static SubscriptionService subscriptionService;

    private static RoleService roleService;

    @PostConstruct
    public void init() {
        userDTO= new UserDTO();
        loginService = new LoginService();
        roleService = new RoleService();
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public String submit(){
        UserModel userModel = loginService.verifyAValidLogin(userDTO.getEmail(), userDTO.getUserPassword());
        if(userModel != null){
            var subscriptionModel = subscriptionService.getSubscription(userModel.getId());
            var roleModel = Optional.ofNullable(subscriptionModel)
                    .map(SubscriptionModel::getRoleId)
                    .flatMap(roleId -> roleService.getSubscription(roleId))
                    .orElse(null);
            if(roleModel != null){
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user",userDTO);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("profile",AccountType.valueOf(roleModel.getTypeName()));
            }
            return "success";
        }else{
            FacesContext.getCurrentInstance().
                    addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error Message", "Message Content"));
            return "error";
        }
    }
}