package com.bb.kalah.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Sow result specifies the sowed entity, end pit of the entity, player to which entity belongs and remaining seeds to sow
 */
@Getter
@AllArgsConstructor
public class SowResult {
    PlayerPart playerPart;
    Sowable sowable;
    int remainingSeedsAmount;
    int endPit;

    public boolean isKalah(){
        return endPit == Kalah.PIT_ID;
    }
}
