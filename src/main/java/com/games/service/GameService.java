package com.games.service;

import com.games.model.Game;

import java.util.List;

public interface GameService {
    Game createGame(Game game);
    Game getGameByName(String name);
    List<Game> getAllGames();
    Game updateGame(String name, Game game);
    void deleteGame(String name);
}
