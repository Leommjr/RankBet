package br.com.rankbet.template;

import br.com.rankbet.model.game.Game;

import java.util.List;

public class GameTemplate {

    public static List<Game> validGame() {
        return List.of(new Game("Flamengo", "1", "Bet3", "BRASILEIRÃO - A", "Flamengo", "Palmeiras", 1.95f, 0.6f, "url"));
    }

}
