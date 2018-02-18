package com.bb.kalah.controller;

import com.bb.kalah.model.GameSession;
import com.bb.kalah.model.PlayerMoveResult;
import com.bb.kalah.model.WinnerCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.bb.kalah.model.PlayerMoveResult.*;

@Component
public class MessageProvider {

    @Autowired
    private WinnerCalculator winnerCalculator;

    private Map<PlayerMoveResult, String> messages = new HashMap<>();

    public MessageProvider(){
        messages.put(SUCCESS, "Move successful");
        messages.put(SUCCESS_GAME_OVER, "Game over! The winner is %s");
        messages.put(ERR_GAME_IS_OVER, "Error: Moves not allowed after game is ended");
        messages.put(ERR_INVALID_PIT_INDEX, "Error: Invalid pit index '%d' expected a value in range [0;%d]");
        messages.put(ERR_PIT_IS_EMPTY, "Error: The selected pit is empty");
        messages.put(ERR_OPPONENT_TURN, "Error: It is opponent's turn");
        messages.put(ERR_PLAYER_NOT_FOUND, "Error: Player id=%d not found in game session id=%d");
    }

    public String getMessage(PlayerMoveResult moveResult, GameSession gameSession) {
        String msg = messages.get(moveResult);
        if(msg == null || msg.isEmpty()) {
            return "Error: Unexpected move result";
        }
        switch (moveResult) {
            case SUCCESS_GAME_OVER:
                msg = String.format(msg, winnerCalculator.getWinner(gameSession).getName());
                break;
            case ERR_INVALID_PIT_INDEX:
                msg = String.format(msg, gameSession.getLastMove().getStartPit(), gameSession.getPlayerA().getPits().getPitsNumber()-1);
                break;
            case ERR_PLAYER_NOT_FOUND:
                msg = String.format(msg, gameSession.getLastMove().getPlayerId());
                break;
                default:
                    // do nothing
        }
        return msg;
    }

}
