package br.com.rankbet.controller;

import br.com.rankbet.enums.AccountType;
import br.com.rankbet.model.SubscriptionModel;
import br.com.rankbet.model.dto.UserDTO;
import br.com.rankbet.service.RoleService;
import br.com.rankbet.service.SubscriptionService;
import br.com.rankbet.service.UserService;
import br.com.rankbet.utils.PasswordUtil;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.time.LocalDateTime;

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

    private String newPasseword;

    private String account;

    private PasswordUtil passwordUtil;

    private static RoleService roleService;

    private static SubscriptionService subscriptionService = new SubscriptionService();


    @PostConstruct
    public void init() {
        userDTO= new UserDTO();
        userService = new UserService();
    }

    public void submit(){
        try{
            if(validatePassword()){
                userDTO.setUserPassword(passwordUtil.generateMD5(newPasseword));
            }else{
                throw new IllegalArgumentException("As senhas não são correspondentes");
            }
            userService.registerUser(userDTO);
            createSubscription(userDTO.getEmail(),account);
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("login.xhtml");
        }catch (Exception exception){
            exception.printStackTrace();
            FacesContext.getCurrentInstance().
                    addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error Message", "Message Content"));
        }
    }
    private boolean validatePassword(){
        return userDTO.getUserPassword().equalsIgnoreCase(newPasseword) ? true : false;
    }

    private boolean createSubscription(String email,String account){
        try{
            var freeRole = roleService.findRole(AccountType.valueOf(account).toString());
            var user = userService.getUser(email);
            SubscriptionModel subscriptionModel = new SubscriptionModel();
            subscriptionModel.setPrice(0f);
            subscriptionModel.setCreateAt(LocalDateTime.now());
            subscriptionModel.setUpdateAt(LocalDateTime.now());
            subscriptionModel.setRoleId(freeRole.getId());
            subscriptionModel.setExpiresAt(null);
            subscriptionModel.setUserId(user.getId());
            subscriptionService.save(subscriptionModel);
            return true;
        }catch (Exception exception){
            FacesContext.getCurrentInstance().
                    addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error Message", "Message Content"));
        }
        return false;
    }

    public String getNewPasseword() {
        return newPasseword;
    }

    public void setNewPasseword(String newPasseword) {
        this.newPasseword = newPasseword;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
