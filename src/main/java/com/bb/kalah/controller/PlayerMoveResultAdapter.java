package com.bb.kalah.controller;

import com.bb.kalah.model.GameSession;
import com.bb.kalah.model.PlayerMoveResult;
import com.bb.kalah.view.PlayerMoveResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerMoveResultAdapter {

    @Autowired
    private GameSessionAdapter gameSessionAdapter;
    @Autowired
    private MessageProvider messageProvider;

    public PlayerMoveResultView fromModel(GameSession gameSession){
        PlayerMoveResultView playerMoveResultView = new PlayerMoveResultView();
        playerMoveResultView.setGameSessionView(gameSessionAdapter.fromModel(gameSession));

        PlayerMoveResult playerMoveResult = gameSession.getLastMoveResult();
        if(playerMoveResult == null){
            playerMoveResultView.setIsSuccesful(null);
            playerMoveResultView.setMessage(null);
        } else {
            playerMoveResultView.setIsSuccesful(isMessageSuccess(playerMoveResult));
            playerMoveResultView.setMessage(messageProvider.getMessage(playerMoveResult, gameSession));
        }

        return playerMoveResultView;
    }

    private boolean isMessageSuccess(PlayerMoveResult moveResult) {
        return PlayerMoveResult.SUCCESS.equals(moveResult) || PlayerMoveResult.SUCCESS_GAME_OVER.equals(moveResult);
    }
}