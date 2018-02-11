package com.bb.kalah.model;

import org.junit.Before;

public class TestRoot {

    String playerAName = "Anna";
    String playerBName = "Bob";

    GameSession gameSession;

    @Before
    public void createSession(){
        gameSession = GameSessionManager.INSTANCE.newGameSession(playerAName, playerBName);
    }
}