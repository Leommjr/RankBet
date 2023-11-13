package br.com.rankbet.service;


import br.com.rankbet.dao.UserDAO;
import br.com.rankbet.model.UserModel;
import br.com.rankbet.utils.PasswordUtil;

public class LoginService {

    private UserDAO userDAO;

    public LoginService(){
        this.userDAO =  new UserDAO();
    }

    public LoginService(UserDAO userDAO){
        this.userDAO =  userDAO;
    }

    public UserModel verifyAValidLogin(String email, String password) {
        String md5Password = PasswordUtil.generateMD5(password);
        UserModel temp = userDAO.findByEmail(email);
        if(temp != null){
            if (temp.getUserPassword().equalsIgnoreCase(md5Password)){
                return temp;
            }
            else{
                return null;
            }
        }
        return null;
    }

}
