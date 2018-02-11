package com.bb.kalah.model;

import lombok.Data;

@Data
public class PlayerPart {

    private static final int SEEDS_PER_PIT = 6;
    private long id;
    private String name;
    private PlayerPits pits;
    Kalah kalah;

    public PlayerPart(long id, String name){
        this.id = id;
        this.name = name;
        this.pits = new PlayerPits(this, SEEDS_PER_PIT);
        this.kalah = new Kalah(this);
    }

}
