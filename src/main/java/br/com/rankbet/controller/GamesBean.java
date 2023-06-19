package br.com.rankbet.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.rankbet.service.LiveGamesService;
import br.com.rankbet.model.game.Game;

@Named
@RequestScoped
public class GamesBean {

    @Inject
    private LiveGamesService liveGamesService;

    private List<Game> games;
    private String searchTerm;
    private List<Game> filteredGames;

    private Game selectedGame;


    public List<Game> getGames() {
        return games;
    }


    public String getSearchTerm() {
        return searchTerm;
    }


    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }


    public List<Game> getFilteredGames() {
        return filteredGames;
    }


    public void setFilteredGames(List<Game> filteredGames) {
        this.filteredGames = filteredGames;
    }


    public void liveGames() {
        try {
            liveGamesService.refreshLiveGames();
            games = liveGamesService.getAllLiveGames();
            filteredGames = new ArrayList<>(games);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("myform", new FacesMessage("Erro ao extrair dados da API"));
        }
    }

    public void selectTeam(Game game, String team) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/security/team.xhtml?id="+game.getId()+"&time="+game.getTime()+"&team="+team);
    }

    public void refreshLiveGames() {
        liveGamesService.refreshLiveGames();
        PrimeFaces.current().ajax().update("liveGamesTable");
    }

    public void filterGames() {
        if (searchTerm == null || searchTerm.isEmpty()) {
            filteredGames = games;
        } else {
            filteredGames = games.stream()
                    .filter(g -> g.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }



}