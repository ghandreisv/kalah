package com.bb.kalah.model;

import lombok.Getter;

/**
 * Player Kalah
 */
@Getter
public class Kalah implements Sowable{

    public static final int PIT_ID = -1;
    int seedsAmount;
    PlayerPart playerPart;

    public Kalah(PlayerPart playerPart){
        this.playerPart = playerPart;
        this.seedsAmount = 0;
    }

    public void addSeeds(int seedsAmount){
        this.seedsAmount +=seedsAmount;
    }

    public void addSeeds(PlayerPits playerPits){
        for(int i=0; i< playerPits.getPitsNumber(); i++) {
            addSeeds(playerPits.pickSeeds(i));
        }
    }

    public SowResult sowSeeds(int seedsAmount, int startPit) {
        this.seedsAmount++;
        return new SowResult(this.playerPart, this, seedsAmount-1, PIT_ID);
    }

    public void reset() {
        this.seedsAmount = 0;
    }

    public String toString(){
        return "( " + this.seedsAmount + " )";
    }
}
