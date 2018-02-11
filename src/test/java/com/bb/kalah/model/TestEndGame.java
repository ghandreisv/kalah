package com.bb.kalah.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestEndGame extends TestRoot {

    @Test
    public void testEndGame() {
        gameSession.getPlayerA().getPits().pits = new int[]{0, 0, 0, 0, 0, 1};
        gameSession.handlePlayerMove(gameSession.getPlayerA().getId(), 5);
        // check pits
        assertTrue("Found non empty pits after game end (Player A)", gameSession.getPlayerA().getPits().isEmpty());
        assertTrue("Found non empty pits after game end (Player B)", gameSession.getPlayerB().getPits().isEmpty());
        // check score
        assertEquals("Wrong end score", 1, gameSession.getPlayerA().getKalah().getSeedsAmount());
        assertEquals("Wrong end score", 36, gameSession.getPlayerB().getKalah().getSeedsAmount());
    }

    @Test
    public void testMoveAfterEndGame() {
        gameSession.getPlayerA().getPits().pits = new int[]{0, 0, 0, 0, 0, 1};
        gameSession.handlePlayerMove(gameSession.getPlayerA().getId(), 5);
        gameSession.handlePlayerMove(gameSession.getPlayerA().getId(), 5);
        assertFalse("Player move should not be allowed after game end", gameSession.getLastMove().isOk());
        // check pits
        assertTrue("Found non empty pits after game end (Player A)", gameSession.getPlayerA().getPits().isEmpty());
        assertTrue("Found non empty pits after game end (Player B)", gameSession.getPlayerB().getPits().isEmpty());
        // check score
        assertEquals("Wrong end score", 1, gameSession.getPlayerA().getKalah().getSeedsAmount());
        assertEquals("Wrong end score", 36, gameSession.getPlayerB().getKalah().getSeedsAmount());
    }
}
