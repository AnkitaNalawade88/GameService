package com.games.service;

import com.games.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class GameServiceTest {

    Game game = new Game();

    @Autowired
    private GameService gameService;

    @BeforeEach
    void setUp() {
        game = Game.builder()
                .active(true)
                .name("AAA")
                .creationDate(new Date())
                .build();
    }


    @Test
    public void testCreateGame() {
        Game createdGame = gameService.createGame(game);
        assertNotNull(createdGame);
        assertEquals("AAA", createdGame.getName());
    }

    @Test
    public void testGetGameByName() {
        Game newGame = new Game(1L, "GameAA", new Date(), true);
        gameService.createGame(newGame);

        Game retrievedGame = gameService.getGameByName("Test Game");

        assertNotNull(retrievedGame);
        assertEquals("Test Game", retrievedGame.getName());
    }

    @Test
    public void testGetAllGames() {
        Game game1 = new Game(1L, "GameAA", new Date(), true);
        Game game2 = new Game(2L, "GameBB", new Date(), true);
        gameService.createGame(game1);
        gameService.createGame(game2);

        List<Game> allGames = gameService.getAllGames();

        assertNotNull(allGames);
        assertEquals(2, allGames.size());
    }

    @Test
    public void testUpdateGame() {
        Game game = new Game(1L, "GameAA", new Date(), true);
        gameService.createGame(game);

        Game result = gameService.updateGame("Update Game", game);

        assertNotNull(result);
        assertEquals("Update Game", result.getName());
    }

    @Test
    public void testDeleteGame() {
        Game newGame = new Game(1L, "GameAA", new Date(), true);
        gameService.createGame(newGame);

        gameService.deleteGame("GameAA");

        assertNull(gameService.getGameByName("GameAA"));
    }
}