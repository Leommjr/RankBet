package br.com.rankbet.service;


import br.com.rankbet.dao.UserDAO;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serializable;
import java.util.*;

import br.com.rankbet.model.game.Game;
import br.com.rankbet.enums.EndpointsEnum;

@ApplicationScoped
public class LiveGamesService implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private Client client;
    private WebTarget target;
    private Jsonb jsonb;

    private List<Game> liveGames;

    public LiveGamesService(){
    }

    public LiveGamesService(Client client, Jsonb jsonb){
        this.client = client;
        this.jsonb = jsonb;
    }


    @PostConstruct
    public void init() {
        ClientConfig clientConfig = new ClientConfig()
                .property(ClientProperties.CONNECT_TIMEOUT, 5000)
                .property(ClientProperties.READ_TIMEOUT, 10000);
        client = ClientBuilder.newClient(clientConfig);
        liveGames = new ArrayList<>();
        refreshLiveGames();
        
    }

    public List<Game> getAllLiveGames() {
        return liveGames;
    }

    public void refreshLiveGames() {
        target = client.target("http://api.rankbet.tech:8000/live");
        jsonb = JsonbBuilder.create();
    	Response response = target.request(MediaType.APPLICATION_JSON).get();
        liveGames = jsonb.fromJson(response.readEntity(String.class), new ArrayList<Game>(){}.getClass().getGenericSuperclass());
    }

    public List<Game> getAllLiveOdds(String id) {
        List<Game> liveOddsfinal = new ArrayList<Game>();
        for(EndpointsEnum endpoint : EndpointsEnum.values()){
            List<Game> liveOdds = new ArrayList<Game>();
            target = client.target(endpoint.getEndpoint());
            jsonb = JsonbBuilder.create();
            Response response = target.request(MediaType.APPLICATION_JSON).get();
            liveOdds = jsonb.fromJson(response.readEntity(String.class), new ArrayList<Game>() {
                }.getClass().getGenericSuperclass());
            liveOdds.stream()
                    .filter(odd -> odd.getId().equals(id))
                    .forEach(liveOddsfinal::add);
        }
        Collections.sort(liveOddsfinal, new Comparator<Game>() {
            @Override
            public int compare(Game game1, Game game2) {
                return Float.compare(game2.getWin1(), game1.getWin1());
            }
        });        Collections.sort(liveOddsfinal, new Comparator<Game>() {
            @Override
            public int compare(Game game1, Game game2) {
                return Float.compare(game2.getWin1(), game1.getWin1());
            }
        });
        return liveOddsfinal;
    }

}
