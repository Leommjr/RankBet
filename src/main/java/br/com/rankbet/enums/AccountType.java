package br.com.rankbet.enums;

public enum AccountType {

    FREE("FREE"),
    PREMIUM1("PREMIUM 1"),
    PREMIUM2("PREMIUM 2");

    public String type;

    AccountType(String type) {
        this.type = type;
    }

    public static String getType(){
        return type;
    }

}
