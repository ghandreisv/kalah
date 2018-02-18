package com.bb.kalah.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerMove {
    private long playerId;
    private PlayerPart player;
    private int startPit;

    public static PlayerMove createPlayerMove(GameSession gameSession, long playerId, int startPit) {
        PlayerPart playerPart = gameSession.getPlayerA().getId() == playerId ? gameSession.getPlayerA() : null;
        playerPart = gameSession.getPlayerB().getId() == playerId ? gameSession.getPlayerB() : playerPart;
        return new PlayerMove(playerId, playerPart, startPit);
    }

    @Override
    public String toString(){
        return "PlayerMove[" + "playerId=" + playerId +
                ";name=" + (player != null ? player.getName() : null) +
                ";startPit=" + startPit + "]";
    }
}
