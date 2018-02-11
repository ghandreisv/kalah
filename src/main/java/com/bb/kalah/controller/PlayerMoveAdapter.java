package com.bb.kalah.controller;

import com.bb.kalah.model.PlayerMove;
import com.bb.kalah.view.PlayerMoveView;
import org.springframework.stereotype.Component;

@Component
public class PlayerMoveAdapter {

    public PlayerMoveView fromModel(PlayerMove playerMove) {
        if (playerMove == null) {
            return null;
        }
        PlayerMoveView playerMoveView = new PlayerMoveView();
        playerMoveView.setPlayerId(playerMove.getPlayerId());
        playerMoveView.setPlayerName(playerMove.getPlayer() == null ? null : playerMove.getPlayer().getName());
        playerMoveView.setStartPid(playerMove.getStartPit());

        return playerMoveView;
    }
}
