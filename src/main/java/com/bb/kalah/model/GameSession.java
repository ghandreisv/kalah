package com.bb.kalah.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class GameSession {

    private Long gameSessionId;

    private PlayerPart playerA;
    private PlayerPart playerB;
    private PlayerPart nextPlayer;

    private long creationTime;
    private GameStatus status;

    private PlayerMove lastMove;

    public GameSession(Long gameSessionId, PlayerPart playerA, PlayerPart playerB){
        this.gameSessionId = gameSessionId;
        this.playerA = playerA;
        this.playerB = playerB;
        this.nextPlayer = playerA;
        this.status = GameStatus.IN_PROGRESS;
        this.creationTime = System.currentTimeMillis();
        log.debug(GameSessionPrinter.INSTANCE.printGameSession(this));
    }

    public void handlePlayerMove(long playerId, int startPit) {
        PlayerMove playerMove = PlayerMove.createPlayerMove(this, playerId, startPit);
        handlePlayerMove(playerMove);
    }

    public void handlePlayerMove(PlayerMove playerMove){
        this.lastMove = playerMove;
        if(playerMove.isOk()) {
            validateMove(playerMove);
            if (playerMove.isOk()) {
                PlayerPart currentPlayer = playerMove.getPlayer();
                int startPit = playerMove.getStartPit();
                int seedsAmount = currentPlayer.getPits().pickSeeds(startPit);
                SowResult sowResult = sowSeeds(
                        new Sowable[]{currentPlayer.getPits(), currentPlayer.getKalah(), getOtherPlayer(currentPlayer).getPits()},
                        startPit + 1, seedsAmount);
                endPlayerMove(currentPlayer, sowResult);
            }
        }
        log.debug(GameSessionPrinter.INSTANCE.printGameSession(this));
    }

    private void validateMove(PlayerMove playerMove){
        // check game is still running
        if(GameStatus.ENDED.equals(status)){
            playerMove.errorResult("Moves not allowed after game is ended");
            return;
        }
        //check it is player's turn
        if(playerMove.getPlayer() != nextPlayer) {
            playerMove.errorResult("It is opponent's turn");
            return;
        }
        //check pit index is in range
        if(playerMove.getStartPit() > playerMove.getPlayer().getPits().getPitsNumber() -1 || playerMove.getStartPit() < 0) {
            playerMove.errorResult(String.format("Invalid pit index '%d' expected a value in range [0;%d]",
                    playerMove.getStartPit(), playerMove.getPlayer().getPits().getPitsNumber() -1));
            return;
        }
        //check pit is not empty
        if(playerMove.getPlayer().getPits().getSeedsAmount(playerMove.getStartPit()) == 0) {
            playerMove.errorResult("The selected pit is empty");
            return;
        }
    }

    private SowResult sowSeeds(Sowable[] sowables, int startPit, int seedsAmount) {
        int sowableIndex = 0;
        SowResult result = null;
        while (seedsAmount > 0) {
            //reset cycle
            if(sowableIndex == sowables.length){
                sowableIndex = 0;
            }
            Sowable sowable = sowables[sowableIndex];
            result = sowable.sowSeeds(seedsAmount, startPit);
            seedsAmount = result.getRemainingSeedsAmount();
            sowableIndex++;
            startPit = 0;
        }
        return result;
    }

    private void endPlayerMove(PlayerPart currentPlayer, SowResult sowResult) {
        //check for opponent seeds collect
        if(!sowResult.isKalah() && sowResult.getPlayerPart() == currentPlayer) {
            int seedsAmount = getOtherPlayer(currentPlayer).getPits().pickMirrorSeeds(sowResult.getEndPit());
            currentPlayer.getKalah().addSeeds(seedsAmount);
        }
        //check for win condition
        if(playerA.getPits().isEmpty() || playerB.getPits().isEmpty()) {
            playerA.getKalah().addSeeds(playerA.getPits());
            playerB.getKalah().addSeeds(playerB.getPits());
            closeSession();
        }
        //calc next turn player
        this.nextPlayer = sowResult.isKalah() ? currentPlayer : getOtherPlayer(currentPlayer);
    }

    private PlayerPart getOtherPlayer(PlayerPart playerPart) {
        return playerPart == playerA ? playerB : playerA;
    }

    public void closeSession(){
        this.status = GameStatus.ENDED;
        GameSessionManager.INSTANCE.closeSession(getGameSessionId());
    }

}