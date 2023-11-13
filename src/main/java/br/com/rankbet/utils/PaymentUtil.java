package br.com.rankbet.utils;

import jakarta.faces.context.FacesContext;

public class PaymentUtil {


    public static String getAlphaNumericString(int n)
    {

        // choose a Character random from this String
        String AlphaNumericString = "0123456789";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public static String createBody(String value){
        value = value + "0";
        String total = String.valueOf(Float.parseFloat(value)+3);
        String body = "{\n" +
                "  \"intent\": \"sale\",\n" +
                "  \"payer\": {\n" +
                "    \"payment_method\": \"paypal\"\n" +
                "  },\n" +
                "  \"transactions\": [\n" +
                "    {\n" +
                "      \"amount\": {\n" +
                "        \"total\":" + "\""+ total+"0" +"\",\n" +
                "        \"currency\": \"BRL\",\n" +
                "        \"details\": {\n" +
                "          \"subtotal\":"+ "\"" + value +"\",\n" +
                "          \"tax\": \"1.00\",\n" +
                "          \"shipping\": \"1.00\",\n" +
                "          \"handling_fee\": \"1.00\",\n" +
                "          \"shipping_discount\": \"-1.00\",\n" +
                "          \"insurance\": \"1.00\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"description\": \"The payment transaction description.\",\n" +
                "      \"custom\": \"EBAY_EMS_90048630024435\",\n" +
                "      \"invoice_number\":"+ "\""+ getAlphaNumericString(12) +"\",\n" +
                "      \"payment_options\": {\n" +
                "        \"allowed_payment_method\": \"INSTANT_FUNDING_SOURCE\"\n" +
                "      },\n" +
                "      \"soft_descriptor\": \"ECHI5786786\",\n" +
                "      \"item_list\": {\n" +
                "        \"items\": [\n" +
                "          {\n" +
                "            \"name\": \"hat\",\n" +
                "            \"description\": \"subscription.\",\n" +
                "            \"quantity\": \"1\",\n" +
                "            \"price\":"+ "\"" + value+"\",\n" +
                "            \"tax\": \"1.00\",\n" +
                "            \"sku\": \""+value+"\",\n" +
                "            \"currency\": \"BRL\"\n" +
                "          }"+
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
                "    \"return_url\": \""+"http://app.rankbet.tech:8080"+FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/return.xhtml"+"\",\n" +
                "    \"cancel_url\": \""+"http://app.rankbet.tech:8080"+FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/cancel.xhtml\""+"\n" +
                "  }\n" +
                "}";
        return body;
    }
}
