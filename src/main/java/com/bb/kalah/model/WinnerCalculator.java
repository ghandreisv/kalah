package com.bb.kalah.model;

import org.springframework.stereotype.Component;

@Component
public class WinnerCalculator {

    public PlayerPart getWinner(GameSession gameSession) {
        return gameSession.getPlayerA().getKalah().getSeedsAmount()
                > gameSession.getPlayerB().getKalah().getSeedsAmount() ? gameSession.getPlayerA() : gameSession.getPlayerB();
    }
}
