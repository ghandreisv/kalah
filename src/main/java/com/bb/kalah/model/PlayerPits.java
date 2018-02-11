package com.bb.kalah.model;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Player pits
 */
public class PlayerPits implements Sowable {

    private static final int PITS_NR = 6;

    int[] pits;
    int seedsPerPit;
    PlayerPart playerPart;

    public PlayerPits(PlayerPart playerPart, int seedsPerPit) {
        this.playerPart = playerPart;
        this.seedsPerPit = seedsPerPit;
        this.pits = new int[PITS_NR];
        this.reset();
    }

    public void reset() {
        for (int i = 0; i < pits.length; i++) {
            pits[i] = seedsPerPit;
        }
    }

    public int pickSeeds(int pitIndex) {
        int seedsAmount = pits[pitIndex];
        pits[pitIndex] = 0;
        return seedsAmount;
    }

    public int pickMirrorSeeds(int pitIndex) {
        return pickSeeds(pits.length - pitIndex - 1);
    }

    public int getSeedsAmount(int pitIndex){
        return pits[pitIndex];
    }

    /**
     * adds seeds to given pits
     * @param seedsAmount amount of seeds to add
     * @param startPit start pit
     * @return sow result
     */
    public SowResult sowSeeds(int seedsAmount, int startPit){
        //some checks, should never happen though
        if(seedsAmount<=0) {
            throw new IllegalArgumentException("Invalid seeds amount expected a value greater than 0");
        }
        if(startPit <0) {
            throw new IllegalArgumentException("Invalid pit index expected a positive value");
        }
        int i = startPit;
        while(seedsAmount > 0 && i < pits.length){
            pits[i]++;
            seedsAmount--;
            i++;
        }
        i--;
        return new SowResult(this.playerPart, this, seedsAmount, i);
    }

    public int getPitsNumber(){
        return pits.length;
    }

    public boolean isEmpty(){
        return IntStream.of(pits).noneMatch(pit -> pit > 0);
    }

    public String toString(){
        return IntStream.of(pits).mapToObj(String::valueOf)
                .collect(Collectors.joining(" | ", "| ", " |")) +
                System.getProperty("line.separator") +
                IntStream.range(0, pits.length).mapToObj(String::valueOf)
                        .collect(Collectors.joining(" * ", "* ", " *"));
    }

    public int[] getPits() {
        return pits;
    }
}
