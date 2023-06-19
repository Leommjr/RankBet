package br.com.rankbet.enums;

public enum AccountType {

    FREE("FREE"),
    PREMIUM1("PREMIUM1"),
    PREMIUM2("PREMIUM2");

    public String type;

    AccountType(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }

}
