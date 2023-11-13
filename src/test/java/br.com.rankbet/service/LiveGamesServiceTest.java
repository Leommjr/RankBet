package br.com.rankbet.service;

import br.com.rankbet.model.game.Game;
import br.com.rankbet.template.GameTemplate;
import com.google.protobuf.compiler.PluginProtos;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.primefaces.shaded.json.JSONObject;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LiveGamesServiceTest {

    @InjectMocks
    private LiveGamesService liveGamesService;
    private Client mockedClient;
    private WebTarget mockedWebTarget;
    private Response mockedResponse;

    private Jsonb jsonObject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockedClient = Mockito.mock(Client.class);
        jsonObject = Mockito.mock(Jsonb.class);
        liveGamesService = new LiveGamesService(mockedClient, jsonObject);
        mockedWebTarget = Mockito.mock(WebTarget.class);
        mockedResponse = Mockito.mock(Response.class);
    }

    @Test
   public void testRefreshLiveGamesWhenCalledSucceeds() {
        liveGamesService.init();
        Jsonb jsonObject = Mockito.mock(Jsonb.class);
        Mockito.when(jsonObject.fromJson(Mockito.any(Reader.class), Mockito.any())).thenReturn(GameTemplate.validGame());
        liveGamesService.refreshLiveGames();
        List<Game> games = liveGamesService.getAllLiveGames();
        Assert.assertNotNull(games);
    }

    @Test
    public void testGetAllLiveOddsWhenIdExistsReturnsFilteredGames() {
        liveGamesService.init();
        Mockito.when(jsonObject.fromJson(Mockito.any(Reader.class), Mockito.any())).thenReturn(GameTemplate.validGame());
        List<Game> games = liveGamesService.getAllLiveOdds("1");
        Assert.assertNotNull(games);
    }

//    @Test
//    public void testGetAllLiveOdds_WhenIdExists_ReturnsFilteredGames() {
//
//        liveGamesService.init();
//
//        String simulatedJson = "[{}]";
//        Mockito.when(mockedResponse.readEntity(String.class)).thenReturn(simulatedJson);
//
//        Jsonb jsonObject = Mockito.mock(Jsonb.class);
//        Mockito.when(jsonObject.fromJson(simulatedJson, new ArrayList<Game>(){}.getClass().getGenericSuperclass()))
//                .thenReturn(GameTemplate.validGame());
//
//        // Chamar o método que você deseja testar
//        List<Game> result = liveGamesService.getAllLiveOdds("1"); // Passando o ID como exemplo
//
//        // Verificar se a lista retornada não é nula e se contém os Games esperados
//        Assert.assertNotNull(result);
//        Assert.assertEquals(1, result.size()); // Espera-se que exista um único Game com o ID fornecido no exemplo
//        Assert.assertEquals("123", result.get(0).getId()); // Verificando se o ID corresponde ao filtrado
//    }

}