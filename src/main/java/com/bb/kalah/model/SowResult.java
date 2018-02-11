package com.bb.kalah.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
