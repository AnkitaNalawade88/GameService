package com.games.controller;

import com.games.model.Game;
import com.games.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    private final Logger LOGGER =
            LoggerFactory.getLogger(GameController.class);

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody (required = false) Game game) {
        LOGGER.info("Inside createGame of GameController");
        Game createdGame = gameService.createGame(game);
        return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Game> getGameByName(@PathVariable String name) {
        Game game = gameService.getGameByName(name);
        if (game != null) {
            return new ResponseEntity<>(game, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Void> updateGame(@PathVariable String name, @RequestBody Game game) {
        gameService.updateGame(name, game);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteGame(@PathVariable String name) {
        gameService.deleteGame(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}