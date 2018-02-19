package com.bb.kalah.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestMove extends TestRoot {

    @Test
    public void testMoveCalc(){
        gameSession.handlePlayerMove(gameSession.getPlayerA().getId(), 0);
        assertEquals(0, gameSession.getPlayerA().getPits().getSeedsAmount(0));
        assertEquals(1, gameSession.getPlayerA().getKalah().getSeedsAmount());
        assertEquals("Expected successful move", PlayerMoveResult.SUCCESS, gameSession.getLastMoveResult());

        gameSession.handlePlayerMove(gameSession.getPlayerA().getId(), 1);
        assertEquals(0, gameSession.getPlayerA().getPits().getSeedsAmount(1));
        assertEquals(2, gameSession.getPlayerA().getKalah().getSeedsAmount());
        assertEquals(8, gameSession.getPlayerA().getPits().getSeedsAmount(5));
        assertEquals("Expected successful move", PlayerMoveResult.SUCCESS, gameSession.getLastMoveResult());
        assertEquals(gameSession.getNextPlayer(), gameSession.getPlayerB());

        gameSession.handlePlayerMove(gameSession.getPlayerB().getId(), 0);
        assertEquals("Expected successful move", PlayerMoveResult.SUCCESS, gameSession.getLastMoveResult());
        assertEquals(0, gameSession.getPlayerB().getPits().getSeedsAmount(0));
        assertEquals(1, gameSession.getPlayerB().getKalah().getSeedsAmount());
    }

    @Test
    public void testSixOnSixFullGame(){
        while(!PlayerMoveResult.SUCCESS_GAME_OVER.equals(gameSession.getLastMoveResult())
                && gameSession.getMoveNumber().get() < 50){ //prevent infinite looping
            PlayerPart playerPart = gameSession.getNextPlayer();
            int[] pits = playerPart.getPits().getPits();
            int index = 0;
            for(int i=0; i<pits.length;i++){
                if(pits[i] > 0) {
                    index = i;
                    break;
                }
            }
            gameSession.handlePlayerMove(playerPart.getId(), index);
        }
        // check move result
        assertEquals("Expected successful game over", PlayerMoveResult.SUCCESS_GAME_OVER, gameSession.getLastMoveResult());
        // check pits
        assertTrue("Found non empty pits after game end (Player A)", gameSession.getPlayerA().getPits().isEmpty());
        assertTrue("Found non empty pits after game end (Player B)", gameSession.getPlayerB().getPits().isEmpty());
        // check score
        assertEquals("Wrong end score", 50, gameSession.getPlayerA().getKalah().getSeedsAmount());
        assertEquals("Wrong end score", 22, gameSession.getPlayerB().getKalah().getSeedsAmount());
        // check moves number
        assertEquals("Wrong moves number", 27, gameSession.getMoveNumber().get());
        // check winner
        assertEquals("Wrong winner", gameSession.getPlayerA(), gameSession.getWinner());
    }

    @Test
    public void testPitSteal(){
        gameSession.getPlayerA().getPits().pits[0] = 1;
        gameSession.getPlayerA().getPits().pits[1] = 0;
        gameSession.handlePlayerMove(gameSession.getPlayerA().getId(), 0);
        assertEquals("Expected successful move", PlayerMoveResult.SUCCESS, gameSession.getLastMoveResult());
        assertEquals("Wrong player A collected pit amount", 0, gameSession.getPlayerA().getPits().getSeedsAmount(0));
        assertEquals("Wrong player B mirror pit amount", 0, gameSession.getPlayerB().getPits().getSeedsAmount(4));
        assertEquals("Wrong player A Kalah score", 7, gameSession.getPlayerA().getKalah().getSeedsAmount());
    }

    @Test
    public void testWrongPlayer() {
        gameSession.handlePlayerMove(gameSession.getPlayerB().getId(), 0);
        assertEquals(PlayerMoveResult.ERR_OPPONENT_TURN, gameSession.getLastMoveResult());
    }

    @Test
    public void testUnknownPlayer() {
        gameSession.handlePlayerMove(-1, 0);
        assertEquals("Expected player not found error", PlayerMoveResult.ERR_PLAYER_NOT_FOUND, gameSession.getLastMoveResult());
    }

    @Test
    public void testInvalidPitIndexMin() {
        gameSession.handlePlayerMove(gameSession.getPlayerA().getId(), -1);
        assertEquals("Expected invalid pit id error", PlayerMoveResult.ERR_INVALID_PIT_INDEX, gameSession.getLastMoveResult());
    }

    @Test
    public void testInvalidPitIndexMax() {
        gameSession.handlePlayerMove(gameSession.getPlayerA().getId(), gameSession.getPlayerA().getPits().getPitsNumber());
        assertEquals("Expected invalid pit id error", PlayerMoveResult.ERR_INVALID_PIT_INDEX, gameSession.getLastMoveResult());
    }

    @Test
    public void testEmptyPit(){
        gameSession.getPlayerA().getPits().pits = new int[]{0, 0, 0, 0, 0, 1};
        gameSession.handlePlayerMove(gameSession.getPlayerA().getId(), 1);
        assertEquals("Expected empty pit error", PlayerMoveResult.ERR_PIT_IS_EMPTY, gameSession.getLastMoveResult());
    }


}
