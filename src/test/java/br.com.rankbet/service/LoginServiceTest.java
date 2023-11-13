package br.com.rankbet.service;

import br.com.rankbet.dao.UserDAO;
import br.com.rankbet.model.UserModel;
import br.com.rankbet.template.UserModelTemplate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class LoginServiceTest {

    @Mock
    UserDAO userDAO;
    @InjectMocks
    private LoginService loginService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.loginService = new LoginService(userDAO);
    }

    @Test
    public void givenAvalidEmailAndPasswordShouldReturnAUser(){
        UserModel user = UserModelTemplate.validUserModel();
        Mockito.when(userDAO.findByEmail(Mockito.anyString())).thenReturn(user);
        Assert.assertNotNull(loginService.verifyAValidLogin(user.getEmail(),"xxxxxx"));
    }


    @Test
    public void givenAInvalidEmailAndPasswordDontShouldReturnAUser(){
        UserModel user = UserModelTemplate.validUserModel();
        Mockito.when(userDAO.findByEmail(Mockito.anyString())).thenReturn(user);
        Assert.assertNull(loginService.verifyAValidLogin(user.getEmail(),"senhaErrada"));
    }

}