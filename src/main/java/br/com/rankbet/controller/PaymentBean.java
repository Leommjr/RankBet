package br.com.rankbet.controller;

import br.com.rankbet.exception.BusinessException;
import br.com.rankbet.model.SubscriptionModel;
import br.com.rankbet.model.UserModel;
import br.com.rankbet.service.PaymentService;
import br.com.rankbet.service.SubscriptionService;
import br.com.rankbet.service.UserService;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.primefaces.shaded.json.JSONObject;

import java.io.IOException;
import java.util.Map;

@Named
@RequestScoped
@ManagedBean
public class PaymentBean {

    private PaymentService paymentService;
    private String payerid;
    private String paymentId;

    UserService userService;

    SubscriptionService subscriptionService;
    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public String getPayerid() {
        return payerid;
    }

    public void setPayerid(String payerid) {
        this.payerid = payerid;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    @PostConstruct
    public void init() throws IOException, BusinessException {
        paymentService = new PaymentService();
        userService = new UserService();
        subscriptionService = new SubscriptionService();
        Map<String, String> params =FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
         paymentId = params.get("paymentId");
        payerid = params.get("PayerID");


        String body = "{\"payer_id\":\""+payerid+"\"}";
        body = body.replace(" ","");
        String url = "https://api-m.sandbox.paypal.com/v1/payments/payment/"+paymentId+"/execute";
        String result = paymentService.execute(url,body );
        if(!result.isEmpty() && !result.equals("FAIL")){

            String email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userEmail");
            long roleId= (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roleID");
            UserModel user = userService.getUser(email);
            SubscriptionModel sub = subscriptionService.getSubscription(user.getId());
            sub.setRoleId(roleId);
            subscriptionService.saveOrUpdate(sub);
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/login.xhtml");
        }
        ;
    }

//    public void reqPayment() throws IOException {
//        //PEGA O ACCESS TOKEN
//        paymentService.stringRequest("https://api-m.sandbox.paypal.com/v1/oauth2/token","");
//        String linkRedirect = paymentService.stringRequest("https://api-m.sandbox.paypal.com/v1/payments/payment",body);
//        FacesContext.getCurrentInstance().getExternalContext().redirect(linkRedirect);
//    }
//
//    public void executePayment() throws IOException {
//        //PEGA O ACCESS TOKEN
//        String accessToken = paymentService.stringRequest("https://api-m.sandbox.paypal.com/v1/oauth2/token","");
//        String linkRedirect = paymentService.stringRequest("https://api-m.sandbox.paypal.com/v1/payments/payment",body);
//        FacesContext.getCurrentInstance().getExternalContext().redirect(linkRedirect);
//    }

}