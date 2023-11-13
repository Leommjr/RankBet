package br.com.rankbet.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.primefaces.shaded.json.JSONObject;

public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Before
    public void setup() {
        paymentService = new PaymentService();
    }

    @Test
    public void givenAStringRequestWhenValidURLAndBodyReturnsValidToken() {
        String url = "https://api-m.sandbox.paypal.com/v1/oauth2/token";
        String body = "";
        JSONObject jsonObject = Mockito.mock(JSONObject.class);
        Mockito.when(jsonObject.getString(Mockito.anyString())).thenReturn("token");
        String token = paymentService.stringRequest(url, body);
        Assert.assertNotNull(token);
        Assert.assertFalse(token.isEmpty());
    }

    @Test
    public void givenAStringRequestWhenGivenInvalidURLReturnsFail() {
        String url = "https://invalid-url";
        String body = "";
        JSONObject jsonObject = Mockito.mock(JSONObject.class);
        Mockito.when(jsonObject.getString(Mockito.anyString())).thenReturn(null);
        String result = paymentService.stringRequest(url, body);
        Assert.assertEquals("FAIL", result);
    }

    @Test
    public void testExecuteWhenGivenInvalidURLReturnsFail() {
        String url = "https://invalid-url";
        String body = "";
        String result = paymentService.execute(url, body);
        Assert.assertEquals("FAIL", result);
    }
}