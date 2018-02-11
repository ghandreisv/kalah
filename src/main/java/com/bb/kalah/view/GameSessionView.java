package com.bb.kalah.view;

import lombok.Data;

@Data
public class GameSessionView {

    private long gameId;
    private PlayerPartView playerA;
    private PlayerPartView playerB;
    private String gameStatus;
    private long nextTurnPlayerId;
    private PlayerMoveView previousMove;

}
