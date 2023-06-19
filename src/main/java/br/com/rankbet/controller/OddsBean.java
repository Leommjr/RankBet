package br.com.rankbet.controller;

import br.com.rankbet.model.game.Game;
import br.com.rankbet.service.LiveGamesService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

import java.io.IOException;
import java.util.List;


@Named
@RequestScoped
public class OddsBean implements java.io.Serializable {

    @Inject
    private LiveGamesService liveGamesService;

    private List<Game> odds;

    private String accountType;

    public void liveOdds(String id) {
        try {
            accountType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("profile");
            odds = liveGamesService.getAllLiveOdds(id);
            if("FREE" == accountType){
                if (!odds.isEmpty()) {
                    odds.get(0).setWin1(0);
                    odds.get(0).setWin2(0);
                    odds.get(0).setBetName("Odds exclusiva para usuários PREMIUM");
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("myform", new FacesMessage("Erro ao extrair dados da API"));
        }
    }

    public List<Game> getOdds() {
        return odds;
    }

    public void setOdds(List<Game> odds) {
        this.odds = odds;
    }

    public Game getById(String id) {
        for (Game odd : odds) {
            if (odd.getId().equals(id)) {
                return odd;
            }
        }
        return null;
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean isPremium() {
        return accountType == "PREMIUM1" || accountType == "PREMIUM2";
    }


}