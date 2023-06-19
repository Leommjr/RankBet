package br.com.rankbet.controller;

import br.com.rankbet.dao.PriceDAO;
import br.com.rankbet.enums.AccountType;
import br.com.rankbet.model.PriceModel;
import br.com.rankbet.model.RoleModel;
import br.com.rankbet.model.SubscriptionModel;
import br.com.rankbet.model.dto.UserDTO;
import br.com.rankbet.service.PaymentService;
import br.com.rankbet.service.RoleService;
import br.com.rankbet.service.SubscriptionService;
import br.com.rankbet.service.UserService;
import br.com.rankbet.utils.PasswordUtil;
import br.com.rankbet.utils.PaymentUtil;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    PriceDAO priceDAO = new PriceDAO();
    PaymentService paymentService;

    private static SubscriptionService subscriptionService = new SubscriptionService();


    @PostConstruct
    public void init() {
        userDTO= new UserDTO();
        userService = new UserService();
        roleService = new RoleService();
        paymentService = new PaymentService();
    }

    public void submit(){
        try{
            if(validatePassword()){
                userDTO.setUserPassword((newPasseword));
            }else{
                throw new IllegalArgumentException("As senhas não são correspondentes");
            }
            userService.registerUser(userDTO);
            createSubscription(userDTO.getEmail(),"FREE");
            if(!AccountType.valueOf(account).equals("FREE")){
                List<RoleModel> roleModelList = roleService.findAll();
                String role = account.replace("PREMIUM", "PREMIUM ");
                List<RoleModel> result = roleModelList.stream().filter( c -> c.getTypeName().equals( role ) ).collect( Collectors.toList() );
                PriceModel current = priceDAO.findByRoleID(result.get(0).getId());
                String body = PaymentUtil.createBody(String.valueOf( current.getCurrentPrice() -3 ));
                String url = "https://api-m.sandbox.paypal.com/v1/payments/payment";
                String resultString = paymentService.execute(url,body );
                if(!resultString.isEmpty() && !resultString.equals("FAIL")){

                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userEmail",userDTO.getEmail());
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("roleID",result.get(0).getId());
                    FacesContext.getCurrentInstance().getExternalContext()
                            .redirect(resultString);

                }
            }
            else{
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +"/login.xhtml");
            }

        }catch (Exception exception){
            exception.printStackTrace();
            FacesContext.getCurrentInstance().
                    addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error Message", "Message Content"));
        }
    }
    private boolean validatePassword(){
        return userDTO.getUserPassword().equalsIgnoreCase(newPasseword) ? true : false;
    }

    private boolean createSubscription(String email, String account){
        try{
            var freeRole = roleService.findRole(AccountType.valueOf(account).toString());
            var user = userService.getUser(email);
            SubscriptionModel subscriptionModel = new SubscriptionModel();
            subscriptionModel.setPrice(0F);
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
