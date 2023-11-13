package br.com.rankbet.model.dto;

public class UserDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String userName;

    private String userPassword;

    private String doublePassword;




    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getDoublePassword() {
        return doublePassword;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setDoublePassword(String doublePassword) {
        this.doublePassword = doublePassword;
    }

    public UserDTO(String firstName, String lastName, String email, String userName, String userPassword, String doublePassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.userPassword = userPassword;
        this.doublePassword = doublePassword;
    }

    public UserDTO(){}
}
