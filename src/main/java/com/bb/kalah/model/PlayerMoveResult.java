package com.bb.kalah.model;

public enum PlayerMoveResult {
    SUCCESS,
    SUCCESS_GAME_OVER,
    ERR_PLAYER_NOT_FOUND,
    ERR_GAME_IS_OVER,
    ERR_OPPONENT_TURN,
    ERR_INVALID_PIT_INDEX,
    ERR_PIT_IS_EMPTY
}
