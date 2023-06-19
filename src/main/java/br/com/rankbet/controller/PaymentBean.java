package br.com.rankbet.controller;

import br.com.rankbet.service.PaymentService;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;

@Named
@RequestScoped
@ManagedBean
public class PaymentBean {

    private PaymentService paymentService;

    String body = "{\n" +
            "  \"intent\": \"sale\",\n" +
            "  \"payer\": {\n" +
            "    \"payment_method\": \"paypal\"\n" +
            "  },\n" +
            "  \"transactions\": [\n" +
            "    {\n" +
            "      \"amount\": {\n" +
            "        \"total\": \"30.11\",\n" +
            "        \"currency\": \"BRL\",\n" +
            "        \"details\": {\n" +
            "          \"subtotal\": \"30.00\",\n" +
            "          \"tax\": \"0.07\",\n" +
            "          \"shipping\": \"0.03\",\n" +
            "          \"handling_fee\": \"1.00\",\n" +
            "          \"shipping_discount\": \"-1.00\",\n" +
            "          \"insurance\": \"0.01\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"description\": \"The payment transaction description.\",\n" +
            "      \"custom\": \"EBAY_EMS_90048630024435\",\n" +
            "      \"invoice_number\": \"487875896748\",\n" +
            "      \"payment_options\": {\n" +
            "        \"allowed_payment_method\": \"INSTANT_FUNDING_SOURCE\"\n" +
            "      },\n" +
            "      \"soft_descriptor\": \"ECHI5786786\",\n" +
            "      \"item_list\": {\n" +
            "        \"items\": [\n" +
            "          {\n" +
            "            \"name\": \"hat\",\n" +
            "            \"description\": \"Brown hat.\",\n" +
            "            \"quantity\": \"5\",\n" +
            "            \"price\": \"3\",\n" +
            "            \"tax\": \"0.01\",\n" +
            "            \"sku\": \"1\",\n" +
            "            \"currency\": \"BRL\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"handbag\",\n" +
            "            \"description\": \"Black handbag.\",\n" +
            "            \"quantity\": \"1\",\n" +
            "            \"price\": \"15\",\n" +
            "            \"tax\": \"0.02\",\n" +
            "            \"sku\": \"product34\",\n" +
            "            \"currency\": \"BRL\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"shipping_address\": {\n" +
            "          \"recipient_name\": \"Brian Robinson\",\n" +
            "          \"line1\": \"4th Floor\",\n" +
            "          \"line2\": \"Unit #34\",\n" +
            "          \"city\": \"San Jose\",\n" +
            "          \"country_code\": \"US\",\n" +
            "          \"postal_code\": \"95131\",\n" +
            "          \"phone\": \"011862212345678\",\n" +
            "          \"state\": \"CA\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"note_to_payer\": \"Contact us for any questions on your order.\",\n" +
            "  \"redirect_urls\": {\n" +
            "    \"return_url\": \"https://google.com/return\",\n" +
            "    \"cancel_url\": \"https://example.com/cancel\"\n" +
            "  }\n" +
            "}";

    @PostConstruct
    public void init() {
        paymentService = new PaymentService();
    }

    public void reqPayment() throws IOException {
        //PEGA O ACCESS TOKEN
        paymentService.stringRequest("https://api-m.sandbox.paypal.com/v1/oauth2/token","");
        String linkRedirect = paymentService.stringRequest("https://api-m.sandbox.paypal.com/v1/payments/payment",body);
        FacesContext.getCurrentInstance().getExternalContext().redirect(linkRedirect);
    }

    public void executePayment() throws IOException {
        //PEGA O ACCESS TOKEN
        String accessToken = paymentService.stringRequest("https://api-m.sandbox.paypal.com/v1/oauth2/token","");
        String linkRedirect = paymentService.stringRequest("https://api-m.sandbox.paypal.com/v1/payments/payment",body);
        FacesContext.getCurrentInstance().getExternalContext().redirect(linkRedirect);
    }

}