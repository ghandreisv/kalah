package com.bb.kalah.model;

/**
 * Game session printer utility singleton
 */
public enum GameSessionPrinter {

    INSTANCE;

    public String printGameSession(GameSession gameSession) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nGame status: ").append(gameSession.getStatus());
        //last move
        sb.append("\nLast move: ").append(gameSession.getLastMove());
        //last result
        sb.append("\nLast move result: ").append(gameSession.getLastMoveResult());
        /* player A */
        sb.append("\nPlayer ").append(gameSession.getPlayerA().getName());
        if (gameSession.getNextPlayer() == gameSession.getPlayerA()) {
            sb.append(" - your turn");
        }
        /* board status*/
        sb.append("\n").append(gameSession.getPlayerA().getKalah())
                .append(" ").append(new StringBuilder(gameSession.getPlayerA().getPits().toString()).reverse().toString())

                .append("\n").append(gameSession.getPlayerB().getPits())
                .append(" ").append(gameSession.getPlayerB().getKalah())
                /* player B*/
                .append("\nPlayer ").append(gameSession.getPlayerB().getName());

        if (gameSession.getNextPlayer() == gameSession.getPlayerB()) {
            sb.append(" - your turn");
        }
        return sb.toString();
    }
}
