package com.bb.kalah.controller;

import com.bb.kalah.model.GameSession;
import com.bb.kalah.view.GameSessionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameSessionAdapter {

    @Autowired
    private PlayerPartAdapter playerPartAdapter;
    @Autowired
    private PlayerMoveAdapter playerMoveAdapter;

    public GameSessionView fromModel(GameSession gameSession) {
        GameSessionView gameSessionView = new GameSessionView();
        gameSessionView.setGameId(gameSession.getGameSessionId());
        gameSessionView.setGameStatus(gameSession.getStatus().toString());
        gameSessionView.setNextTurnPlayerId(gameSession.getNextPlayer().getId());
        gameSessionView.setPlayerA(playerPartAdapter.fromModel(gameSession.getPlayerA()));
        gameSessionView.setPlayerB(playerPartAdapter.fromModel(gameSession.getPlayerB()));
        gameSessionView.setPreviousMove(playerMoveAdapter.fromModel(gameSession.getLastMove()));

        return gameSessionView;
    }
}
