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
        // check move result
        assertEquals("Expected successful game over", PlayerMoveResult.SUCCESS_GAME_OVER, gameSession.getLastMoveResult());
        // check pits
        assertTrue("Found non empty pits after game end (Player A)", gameSession.getPlayerA().getPits().isEmpty());
        assertTrue("Found non empty pits after game end (Player B)", gameSession.getPlayerB().getPits().isEmpty());
        // check score
        assertEquals("Wrong end score", 1, gameSession.getPlayerA().getKalah().getSeedsAmount());
        assertEquals("Wrong end score", 36, gameSession.getPlayerB().getKalah().getSeedsAmount());
        // check winner
        assertEquals("Wrong winner", gameSession.getPlayerB(), gameSession.getWinner());
    }

    @Test
    public void testMoveAfterEndGame() {
        gameSession.getPlayerA().getPits().pits = new int[]{0, 0, 0, 0, 0, 1};
        gameSession.handlePlayerMove(gameSession.getPlayerA().getId(), 5);
        assertEquals("Expected successful game over", PlayerMoveResult.SUCCESS_GAME_OVER, gameSession.getLastMoveResult());
        gameSession.handlePlayerMove(gameSession.getPlayerA().getId(), 5);
        assertEquals("Player move should not be allowed after game end", PlayerMoveResult.ERR_GAME_IS_OVER, gameSession.getLastMoveResult());
        // check pits
        assertTrue("Found non empty pits after game end (Player A)", gameSession.getPlayerA().getPits().isEmpty());
        assertTrue("Found non empty pits after game end (Player B)", gameSession.getPlayerB().getPits().isEmpty());
        // check score
        assertEquals("Wrong end score", 1, gameSession.getPlayerA().getKalah().getSeedsAmount());
        assertEquals("Wrong end score", 36, gameSession.getPlayerB().getKalah().getSeedsAmount());
    }
}
