package com.bb.kalah.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Game session class, represents an actual game
 */
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
    private PlayerMoveResult lastMoveResult;
    private AtomicInteger moveNumber;

    public GameSession(Long gameSessionId, PlayerPart playerA, PlayerPart playerB){
        this.gameSessionId = gameSessionId;
        this.playerA = playerA;
        this.playerB = playerB;
        this.nextPlayer = playerA;
        this.status = GameStatus.IN_PROGRESS;
        this.creationTime = System.currentTimeMillis();
        this.moveNumber = new AtomicInteger();
        log.debug("New game created:{}", GameSessionPrinter.INSTANCE.printGameSession(this));
    }

    public PlayerMoveResult handlePlayerMove(long playerId, int startPit) {
        PlayerMove playerMove = PlayerMove.createPlayerMove(this, playerId, startPit);
        this.lastMove = playerMove;
        this.moveNumber.incrementAndGet();

        log.debug("Performing player move{}",  GameSessionPrinter.INSTANCE.printGameSession(this));
        this.lastMoveResult = validateMove(playerMove);

        if(PlayerMoveResult.SUCCESS.equals(this.lastMoveResult)) {
                PlayerPart currentPlayer = playerMove.getPlayer();
                int seedsAmount = currentPlayer.getPits().pickSeeds(startPit);
                SowResult sowResult = sowSeeds(
                        new Sowable[]{currentPlayer.getPits(), currentPlayer.getKalah(), getOtherPlayer(currentPlayer).getPits()},
                        startPit + 1, seedsAmount);
                endPlayerMove(currentPlayer, sowResult);
        }
        log.debug("Player move result{}", GameSessionPrinter.INSTANCE.printGameSession(this));
        return lastMoveResult;
    }

    private PlayerMoveResult validateMove(PlayerMove playerMove){
        // check player was found
        if(playerMove.getPlayer() == null) {
            return PlayerMoveResult.ERR_PLAYER_NOT_FOUND;
        }
        // check game is still running
        if(GameStatus.ENDED.equals(status)){
            return PlayerMoveResult.ERR_GAME_IS_OVER;
        }
        //check it is player's turn
        if(playerMove.getPlayer() != nextPlayer) {
            return PlayerMoveResult.ERR_OPPONENT_TURN;
        }
        //check pit index is in range
        if(playerMove.getStartPit() > playerMove.getPlayer().getPits().getPitsNumber() -1 || playerMove.getStartPit() < 0) {
            return PlayerMoveResult.ERR_INVALID_PIT_INDEX;
        }
        //check pit is not empty
        if(playerMove.getPlayer().getPits().getSeedsAmount(playerMove.getStartPit()) == 0) {
            return PlayerMoveResult.ERR_PIT_IS_EMPTY;
        }
        return PlayerMoveResult.SUCCESS;
    }

    /**
     * Perform the actual sowing
     * @param sowables list of the sowable entities e.g. player pits or kalah
     * @param startPit pit index to start the sowing
     * @param seedsAmount amount of seeds to sow
     * @return sowing result
     */
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

    /**
     * tear down actions before player move end like: steal opponent seeds, check win condition etc.
     * @param currentPlayer player who made the move
     * @param sowResult sowing result
     */
    private void endPlayerMove(PlayerPart currentPlayer, SowResult sowResult) {
        //check for opponent seeds collect
        if(!sowResult.isKalah() && (sowResult.getPlayerPart() == currentPlayer)
                && (currentPlayer.getPits().getSeedsAmount(sowResult.getEndPit()) == 1)) {
            int seedsAmount = getOtherPlayer(currentPlayer).getPits().pickMirrorSeeds(sowResult.getEndPit())
                    + currentPlayer.getPits().pickSeeds(sowResult.getEndPit());
            currentPlayer.getKalah().addSeeds(seedsAmount);
        }
        //check for win condition
        if(playerA.getPits().isEmpty() || playerB.getPits().isEmpty()) {
            playerA.getKalah().addSeeds(playerA.getPits());
            playerB.getKalah().addSeeds(playerB.getPits());
            lastMoveResult = PlayerMoveResult.SUCCESS_GAME_OVER;
            GameSessionManager.INSTANCE.closeSession(getGameSessionId());
        }
        //calc next turn player
        this.nextPlayer = sowResult.isKalah() ? currentPlayer : getOtherPlayer(currentPlayer);
    }

    private PlayerPart getOtherPlayer(PlayerPart playerPart) {
        return playerPart == playerA ? playerB : playerA;
    }

    /**
     * ends the game session
     */
    void closeSession(){
        this.status = GameStatus.ENDED;
    }

}
