package com.bb.kalah.controller;

import com.bb.kalah.model.PlayerPart;
import com.bb.kalah.view.PlayerPartView;
import org.springframework.stereotype.Component;

@Component
public class PlayerPartAdapter {

    public PlayerPartView fromModel(PlayerPart playerPart) {
        PlayerPartView playerPartView = new PlayerPartView();
        playerPartView.setPlayerId(playerPart.getId());
        playerPartView.setName(playerPart.getName());
        playerPartView.setKalah(playerPart.getKalah().getSeedsAmount());
        playerPartView.setPits(playerPart.getPits().getPits());

        return playerPartView;
    }
}
