package com.bb.kalah.model;

import com.bb.kalah.model.GameSession;
import com.bb.kalah.model.GameSessionManager;
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