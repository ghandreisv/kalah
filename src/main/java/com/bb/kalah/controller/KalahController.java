package com.bb.kalah.controller;

import com.bb.kalah.model.GameSession;
import com.bb.kalah.model.GameSessionManager;
import com.bb.kalah.view.BadRequestException;
import com.bb.kalah.view.GameSessionView;
import com.bb.kalah.view.MoveResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KalahController {

    @Autowired
    GameSessionAdapter gameSessionAdapter;

    @RequestMapping("/newGame")
    public GameSessionView startGame(@RequestParam(value="playerA", defaultValue="Ann") String playerA,
                                     @RequestParam(value="playerB", defaultValue="Bob") String playerB) {
        GameSession gameSession = GameSessionManager.INSTANCE.newGameSession(playerA, playerB);
        return gameSessionAdapter.fromModel(gameSession);
    }

    @RequestMapping("/move")
    public MoveResultView doMove(@RequestParam(value="game_id", required = false) Long gameId,
                                 @RequestParam(value="player_id", required = false) Long playerId,
                                 @RequestParam(value="start_pit", required = false) Integer startPit) {
        validateMoveParameter("game_id", gameId);
        validateMoveParameter("player_id", playerId);
        validateMoveParameter("start_pit", startPit);
        GameSession gameSession = GameSessionManager.INSTANCE.getGameSession(gameId);
        if(gameSession == null) {
            throw new BadRequestException("Game session id=" + gameId + " not found.");
        }
        MoveResultView moveResultView = new MoveResultView();
        gameSession.handlePlayerMove(playerId, startPit);
        moveResultView.setGameSessionView(gameSessionAdapter.fromModel(gameSession));
        moveResultView.setMessage(gameSession.getLastMove().getMessage());
        moveResultView.setSuccesful(gameSession.getLastMove().isOk());
        return moveResultView;
    }

    private void validateMoveParameter(String paramName, Object paramValue) {
        if(paramValue == null) {
            throw new BadRequestException("Missing parameter '" + paramName
                    + "'. Following parameters are mandatory in move call: gameId, playerId, startPit");
        }
    }
}