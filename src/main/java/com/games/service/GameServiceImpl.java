package com.games.service;

import com.games.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GameServiceImpl implements GameService{
    private final ConcurrentHashMap<String, Game> gameCache = new ConcurrentHashMap<>();

    @Override
    public Game createGame(Game game) {
        gameCache.put(game.getName(), game);
        return game;
    }

    @Override
    public Game getGameByName(String name) {
        return gameCache.get(name);
    }

    @Override
    public List<Game> getAllGames() {
        return gameCache.values().stream().collect(Collectors.toList());
    }

    @Override
    public Game updateGame(String name, Game game) {

        if (gameCache.containsKey(name)) {
            gameCache.put(name, game);
            return game;
        } else {
            throw new IllegalArgumentException("Game not found");
        }
    }

    @Override
    public void deleteGame(String name) {
        gameCache.remove(name);
    }
}