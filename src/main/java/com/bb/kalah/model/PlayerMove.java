package com.bb.kalah.model;

import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
public class PlayerMove {
//TODO: split into move and result
    private static final String OK_MSG = "Success";
    private PlayerPart player;
    private int startPit;

    private boolean isOk = true;
    private String message = OK_MSG;

    public PlayerMove(PlayerPart player, int startPit) {
        this.player = player;
        this.startPit = startPit;
    }
    public void okResult(){
        this.isOk = true;
        this.message = OK_MSG;
    }

    public void errorResult(String message){
        this.isOk = false;
        this.message = message;
    }

    public static PlayerMove createPlayerMove(GameSession gameSession, long playerId, int startPit) {
        PlayerPart playerPart = null;
        playerPart = gameSession.getPlayerA().getId() == playerId ? gameSession.getPlayerA() : null;
        playerPart = gameSession.getPlayerB().getId() == playerId ? gameSession.getPlayerB() : playerPart;
        PlayerMove playerMove = new PlayerMove(playerPart, startPit);
        if(playerPart == null) {
            playerMove.errorResult(String.format("Player id=%d not found in game session id=%d",
                    playerId, gameSession.getGameSessionId()));
        }
        return playerMove;
    }

    @Override
    public String toString(){
        return new StringBuilder("Player=").append(Optional.ofNullable(player).map(Object::toString).orElse("null"))
                .append(";startPit=").append(startPit)
                .append(";isOk=" ).append(isOk).append(";message=").append(message).toString();
    }
}
