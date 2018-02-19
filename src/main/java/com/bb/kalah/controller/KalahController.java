package com.bb.kalah.controller;

import com.bb.kalah.model.GameSession;
import com.bb.kalah.model.GameSessionManager;
import com.bb.kalah.view.BadRequestException;
import com.bb.kalah.view.GameSessionView;
import com.bb.kalah.view.PlayerMoveResultView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class KalahController {

    @Autowired
    private GameSessionAdapter gameSessionAdapter;
    @Autowired
    private PlayerMoveResultAdapter playerMoveResultAdapter;

    @RequestMapping("/newGame")
    public GameSessionView startGame(@RequestParam(value="player_a", defaultValue="Ann") String playerA,
                                     @RequestParam(value="player_b", defaultValue="Bob") String playerB) {
        GameSession gameSession = GameSessionManager.INSTANCE.newGameSession(playerA, playerB);
        GameSessionView gameSessionView = gameSessionAdapter.fromModel(gameSession);
        log.debug("{}", gameSessionView);
        return gameSessionView;
    }

    @RequestMapping("/endGame")
    public GameSessionView endGame(@RequestParam(value="game_id") long gameId) {
        GameSession gameSession = GameSessionManager.INSTANCE.closeSession(gameId);
        if(gameSession == null) {
            throw new BadRequestException("Game session id=" + gameId + " not found");
        }
        GameSessionView gameSessionView = gameSessionAdapter.fromModel(gameSession);
        log.debug("{}", gameSessionView);
        return gameSessionView;
    }

    @RequestMapping("/move")
    public PlayerMoveResultView doMove(@RequestParam(value="game_id", required = false) Long gameId,
                                       @RequestParam(value="player_id", required = false) Long playerId,
                                       @RequestParam(value="start_pit", required = false) Integer startPit) {
        validateMoveParameter("game_id", gameId);
        validateMoveParameter("player_id", playerId);
        validateMoveParameter("start_pit", startPit);
        GameSession gameSession = GameSessionManager.INSTANCE.getGameSession(gameId);
        if(gameSession == null) {
            throw new BadRequestException("Game session id=" + gameId + " not found");
        }
        gameSession.handlePlayerMove(playerId, startPit);
        PlayerMoveResultView playerMoveResultView = playerMoveResultAdapter.fromModel(gameSession);
        log.debug("{}", playerMoveResultView);
        return playerMoveResultView;
    }

    private void validateMoveParameter(String paramName, Object paramValue) {
        if(paramValue == null) {
            throw new BadRequestException("Missing parameter '" + paramName
                    + "'. Following parameters are mandatory in move call: gameId, playerId, startPit");
        }
    }
}