package com.games.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.model.Game;
import com.games.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameControllerTest {

    private Game gm;

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateGame() throws Exception {
        // Create a sample Game object
        Game sampleGame = new Game();
        sampleGame.setName("Sample Game");
        sampleGame.setCreationDate(new Date());
        sampleGame.setActive(true);

        // Serialize the sampleGame to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String gameJson = objectMapper.writeValueAsString(sampleGame);

        // Mock the gameService's createGame method
        when(gameService.createGame(any(Game.class))).thenReturn(sampleGame);

        // Perform the POST request to create a game
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson))
                .andExpect(status().isCreated())
                .andReturn();

        // Verify that the response contains the created game's information
        String responseContent = result.getResponse().getContentAsString();
        Game createdGame = objectMapper.readValue(responseContent, Game.class);

        // Assert the created game's properties
        assert(createdGame.getName().equals("Sample Game"));
        assert(createdGame.isActive());
    }


    @Test
    public void testGetGameByName() throws Exception {
        // Create a sample Game object
        Game sampleGame = new Game();
        sampleGame.setName("Sample Game");
        sampleGame.setActive(true);

        // Mock the gameService's getGameByName method
        when(gameService.getGameByName(anyString())).thenReturn(sampleGame);

        // Perform a GET request to retrieve a game by name
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/games/{name}", "Sample Game")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verify that the response contains the retrieved game's information
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Game retrievedGame = objectMapper.readValue(responseContent, Game.class);

        // Assert the retrieved game's properties
        assert(retrievedGame.getName().equals("Sample Game"));
        assert(retrievedGame.isActive());
    }

    @Test
    public void testGetGameByNameNotFound() throws Exception {
        // Mock the gameService's getGameByName method to return null, indicating a game not found
        when(gameService.getGameByName(anyString())).thenReturn(null);

        // Perform a GET request to retrieve a non-existent game by name
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/games/{name}", "Nonexistent Game")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testGetAllGames() throws Exception {
        // Create a list of sample Game objects
        List<Game> sampleGames = new ArrayList<>();
        Game game1 = new Game();
        game1.setName("Game 1");
        game1.setActive(true);
        sampleGames.add(game1);

        Game game2 = new Game();
        game2.setName("Game 2");
        game2.setActive(false);
        sampleGames.add(game2);

        // Mock the gameService's getAllGames method
        when(gameService.getAllGames()).thenReturn(sampleGames);

        // Perform a GET request to retrieve all games
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/games")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verify that the response contains the list of retrieved games
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Game> retrievedGames = objectMapper.readValue(responseContent, objectMapper.getTypeFactory().constructCollectionType(List.class, Game.class));

        // Assert the retrieved games' properties
        assert(retrievedGames.size() == 2);
        assert(retrievedGames.get(0).getName().equals("Game 1"));
        assert(retrievedGames.get(0).isActive());
        assert(retrievedGames.get(1).getName().equals("Game 2"));
        assert(!retrievedGames.get(1).isActive());
    }

    @Test
    public void testUpdateGame() throws Exception {
        // Create a sample Game object
        Game sampleGame = new Game();
        sampleGame.setName("Sample Game");
        sampleGame.setActive(true);

        // Serialize the sampleGame to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String gameJson = objectMapper.writeValueAsString(sampleGame);

        // Mock the gameService's updateGame method
        when(gameService.updateGame(anyString(), any(Game.class))).thenReturn(sampleGame);

        // Perform a PUT request to update the game
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/games/{name}", "Sample Game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void testDeleteGame() throws Exception {
        // Mock the gameService's deleteGame method
        doNothing().when(gameService).deleteGame(anyString());

        // Perform a DELETE request to delete the game
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/games/{name}", "Sample Game")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}