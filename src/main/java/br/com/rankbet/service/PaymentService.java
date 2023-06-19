package br.com.rankbet.service;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.primefaces.shaded.json.JSONObject;

import java.util.Objects;


@ApplicationScoped
public class PaymentService {

    private Client client;

    private WebTarget target;

    private Jsonb jsonb;

    private final String AUTH = "oauth2";

    private final String CREATE = "/payments/payment";

    private final String userName = "ARs2Zl6aXKO3hVZMdlqzjNliokN0rz6hPy5VPle-2EdWdmiIYf8Msot2Fu3VTwXcDD2Nt2RMtlIFRlFW";

    private final String password = "EF9dSl235R0S4BX2dgd4HOeVnWYMTLWRLwBnTFW_oSD-zC1pt73kpVDWcexo8IbYpMRo113XAiAlrlIz";

    private final String AUTHORIZATION = "Authorization";

    private final String BEARER = "Bearer ";

    public String token;

    @PostConstruct
    public void init() {
        ClientConfig clientConfig = new ClientConfig()
                .property(ClientProperties.CONNECT_TIMEOUT, 5000)
                .property(ClientProperties.READ_TIMEOUT, 10000);
        client = ClientBuilder.newClient(clientConfig);
    }

    public String stringRequest(String url, String body) {
        ClientConfig clientConfig = new ClientConfig()
                .property(ClientProperties.CONNECT_TIMEOUT, 5000)
                .property(ClientProperties.READ_TIMEOUT, 10000);
        client = ClientBuilder.newClient(clientConfig);

        target = client.target(url);
        if(url.contains(AUTH)){
            String responseAuth;
            Form form = new Form();
            form.param("grant_type", "client_credentials");
            final HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(
                    Objects.requireNonNull(userName),
                    Objects.requireNonNull(password)
            );
            target.register(feature);
            Response response = target.request().post(Entity.form(form));
            responseAuth = response.readEntity(String.class);
            token = captureToken(responseAuth);
            if(token.isEmpty()){
            //FACE CONTEXT
                return "FAIL";
            }
            return token;
        }else if(url.endsWith(CREATE)) {
            var r = createPaymentAndExecute(url,body,token,target);
            if(r.isEmpty()){
                //FACE CONTEXT
                return "FAIL";
            }
            if(captureStatus(r, "created")){
                var link  = captureLink(r);
                if(link.isEmpty()){
                    //FACE CONTEXT
                    return "FAIL";
                }else{
                    return link;
                }

            }
        }else{
            var response = createPaymentAndExecute(url,body,token,target);
            if(response.isEmpty()){
                //FACE CONTEXT
                return "FAIL";
            }
            if(captureStatus(response, "approved")){
                //Faces context
                return "SUCCESS";
            }
        }
        // RETORNAR FAIL
        return "FAIL";
    }

    public String createPaymentAndExecute(String url, String body, String token, WebTarget target) {
        try{
            var response = simpleHeaderFluently(AUTHORIZATION,BEARER + token,target,body);
            return response.readEntity(String.class);
        }catch (Exception exception){
            return "";
        }
    }

    public String captureToken(String json){
       try{
           JSONObject jsonObject = new JSONObject(json);
           return jsonObject.getString("access_token");
       }catch (Exception ex){
           return "";
       }
    }

    public Response simpleHeaderFluently(String headerKey, String headerValue, WebTarget target,String body) {
        return target.request(MediaType.APPLICATION_JSON).header(headerKey, headerValue).post(Entity.json(body));
    }

    public boolean captureStatus(String json, String value){
        try{
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString("state").equals(value);
        }catch (Exception ex){
            return false;
        }
    }

    public String captureLink(String json){
        try{
            JSONObject jsonObject = new JSONObject(json);
            var link = jsonObject.getJSONArray("links").get(1).toString();
            JSONObject jsonObjectLink = new JSONObject(link);
            return jsonObjectLink.getString("href");
        }catch (Exception ex){
            return "";
        }
    }
}
