package br.com.rankbet.model;

import java.util.List;

public class ResponseCreate {

    private String id;
    private String intent;
    private String state;
    private String payer;
    private List<String> transactions;
    private String note_to_payer;
    private String create_time;
    private List<String> links;

    private String payment_method;

    private Float amount;
    private String description;
    private String custom;
    private String invoice_number;
    private String soft_descriptor;
    private String payment_options;
    private List<String> item_list;
    private List<String> related_resources;

    private String total;
    private String currency;
    private String details;

    private String subtotal;
    private String tax;
    private String shipping;
    private String insurance;
    private String handling_fee;
    private String shipping_discount;

    private String allowed_payment_method;
    private boolean recurring_flag;
    private boolean skip_fmf;

    private List<String> items;
    private String shipping_address;

}
