package com.bb.kalah.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestCreation extends TestRoot {


    @Test
    public void testNewSession() {
        assertNotNull(gameSession);
        assertNotNull(gameSession.getPlayerA());
        assertNotNull(gameSession.getPlayerB());
        assertEquals(playerAName, gameSession.getPlayerA().getName());
        assertEquals(playerBName, gameSession.getPlayerB().getName());
        assertEquals(0, gameSession.getPlayerA().getKalah().getSeedsAmount());
        assertEquals(0, gameSession.getPlayerB().getKalah().getSeedsAmount());
    }

    @Test
    public void testWinnerUnknown() {
        assertNull("Winner expected to be unknown", gameSession.getWinner());
    }

}
