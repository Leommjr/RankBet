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
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.util.Optional;

@Named
@RequestScoped
@ManagedBean
public class AuthenticationBean {

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    private UserDTO userDTO;

    private LoginService loginService;

    private static SubscriptionService subscriptionService = new SubscriptionService();

    private static RoleService roleService;

    @PostConstruct
    public void init() {
        userDTO= new UserDTO();
        loginService = new LoginService();
        roleService = new RoleService();
    }

    public void submit() throws IOException {
        UserModel userModel = loginService.verifyAValidLogin(userDTO.getEmail(), userDTO.getUserPassword());
        if(userModel != null){
            var subscriptionModel = subscriptionService.getSubscription(userModel.getId());
            var roleModel = Optional.ofNullable(subscriptionModel)
                    .map(SubscriptionModel::getRoleId)
                    .flatMap(roleId -> roleService.getSubscription(roleId))
                    .orElse(null);
            if(roleModel != null){
                String role = roleModel.getTypeName().replace("PREMIUM", "PREMIUM ");
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user",userModel);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("profile",role);
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/security/index.xhtml");
        }else{
            FacesContext.getCurrentInstance().
                    addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error Message", "Invalid email or password"));
        }
    }
    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove( "user" );
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove( "profile" );
        FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( "Logout", "operação realizada com sucesso!" ) );
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/login.xhtml");

    }
}