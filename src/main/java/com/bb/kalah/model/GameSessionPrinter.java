package com.bb.kalah.model;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Game session printer utility singleton
 */
public enum GameSessionPrinter {

    INSTANCE;

    public String printGameSession(GameSession gameSession) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nGame status: ").append(gameSession.getStatus());
        //moves number
        sb.append("\nMove number: ").append(gameSession.getMoveNumber().get());
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
                .append(" ").append(formatInversedPitData(gameSession.getPlayerA().getPits().getPits()))

                .append("\n").append(formatPitData(gameSession.getPlayerB().getPits().getPits()))
                .append(" ").append(gameSession.getPlayerB().getKalah())
                /* player B*/
                .append("\nPlayer ").append(gameSession.getPlayerB().getName());

        if (gameSession.getNextPlayer() == gameSession.getPlayerB()) {
            sb.append(" - your turn");
        }
        return sb.toString();
    }

    private String formatPitData(int[] pits) {
        return IntStream.of(pits).mapToObj(String::valueOf)
                .collect(Collectors.joining(" | ", "| ", " |"))
                    + System.getProperty("line.separator") +
                IntStream.range(0, pits.length).mapToObj(String::valueOf)
                        .collect(Collectors.joining(" * ", "* ", " *"));
    }

    private String formatInversedPitData(int[] pits) {
        return IntStream.range(0, pits.length).map(i -> pits.length - i - 1).mapToObj(String::valueOf)
                .collect(Collectors.joining(" * ", "* ", " *"))
                       + System.getProperty("line.separator") +
                IntStream.range(0, pits.length).map(i -> pits[pits.length - i - 1]).mapToObj(String::valueOf)
                .collect(Collectors.joining(" | ", "| ", " |"));
    }
}
