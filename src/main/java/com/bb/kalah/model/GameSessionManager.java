package com.bb.kalah.model;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Game session manager singletone
 */
public enum GameSessionManager {

    INSTANCE;

    private AtomicLong gamesIdGen = new AtomicLong();
    private AtomicLong partnerIdGen = new AtomicLong();

    private Map<Long, GameSession> games = new ConcurrentHashMap<>();

    public GameSession newGameSession(String playerAName, String playerBName) {
        PlayerPart playerAPart = new PlayerPart(partnerIdGen.getAndIncrement(), playerAName);
        PlayerPart playerBPart = new PlayerPart(partnerIdGen.getAndIncrement(), playerBName);

        GameSession gameSession = new GameSession(gamesIdGen.getAndIncrement(), playerAPart, playerBPart);
        games.put(gameSession.getGameSessionId(), gameSession);

        return gameSession;
    }

    public GameSession getGameSession(long gameSessionId) {
        return games.get(gameSessionId);
    }

    public GameSession closeSession(long gameSessionId) {
        GameSession gameSession = this.games.remove(gameSessionId);
        Optional.ofNullable(gameSession).ifPresent(GameSession::closeSession);
        return gameSession;
    }
}
