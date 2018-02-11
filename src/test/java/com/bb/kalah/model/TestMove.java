package com.bb.kalah.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestMove extends TestRoot {

    @Test
    public void testMoveCalc(){
        gameSession.handlePlayerMove(gameSession.getPlayerA().getId(), 0);
        assertEquals(0, gameSession.getPlayerA().getPits().getSeedsAmount(0));
        assertEquals(1, gameSession.getPlayerA().getKalah().getSeedsAmount());

        gameSession.handlePlayerMove(gameSession.getPlayerA().getId(), 1);
        assertEquals(0, gameSession.getPlayerA().getPits().getSeedsAmount(1));
        assertEquals(2, gameSession.getPlayerA().getKalah().getSeedsAmount());
        assertEquals(8, gameSession.getPlayerA().getPits().getSeedsAmount(5));
        assertEquals(gameSession.getNextPlayer(), gameSession.getPlayerB());

        gameSession.handlePlayerMove(gameSession.getPlayerB().getId(), 0);
        assertEquals(0, gameSession.getPlayerB().getPits().getSeedsAmount(0));
        assertEquals(1, gameSession.getPlayerB().getKalah().getSeedsAmount());
    }

    @Test
    public void testEmptyPitSteal(){
        gameSession.getPlayerA().getPits().pits[0] = 1;
        gameSession.getPlayerA().getPits().pits[1] = 0;
        System.out.println(GameSessionPrinter.INSTANCE.printGameSession(gameSession));
        gameSession.handlePlayerMove(gameSession.getPlayerA().getId(), 0);
        System.out.println(GameSessionPrinter.INSTANCE.printGameSession(gameSession));
        assertEquals("Wrong player A Kalah score", 6, gameSession.getPlayerA().getKalah().getSeedsAmount());
        assertEquals("Wrong player B mirror pit amount", 0, gameSession.getPlayerB().getPits().getSeedsAmount(4));
    }
}
