package br.com.rankbet.controller;

import br.com.rankbet.model.h2h.H2h;
import br.com.rankbet.service.PremiumService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;


@Named
@RequestScoped
public class premiumBean implements java.io.Serializable {
    
    @Inject
    private PremiumService PremiumService;

    private H2h h2h;

    private String accountType;

    public void geth2h(String time, String team1) {
        try {
            accountType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("profile");
            h2h = PremiumService.getH2h(time, team1);
            if("FREE" ==  accountType|| "PREMIUM1" == accountType){
                if (h2h != null) {
                    h2h.setWin1(0);
                    h2h.setWin2(0);
                    h2h.setGoals1(0);
                    h2h.setGoals2(0);
                    h2h.setDraw(0);
                    for (int i = 0; i < h2h.getMatches().size(); i++) {
                        h2h.getMatches().get(i).setScore1(0);
                        h2h.getMatches().get(i).setScore2(0);
                    }
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("myform", new FacesMessage("Erro ao extrair dados da API"));
        }
    }

    public H2h getH2h() {
        return h2h;
    }

    public void setH2h(H2h h2h) {
        this.h2h = h2h;
    }

    public boolean isPremium() {
        return accountType == "PREMIUM1" || accountType == "PREMIUM2";
    }

    public boolean isFreemium() {
        return accountType.equals("FREE");
    }
}
