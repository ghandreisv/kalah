package com.bb.kalah.model;

import lombok.Data;

@Data
public class PlayerMoveResult {

    private static final String OK_MSG = "Success";
    private boolean isOk = true;
    private String message = OK_MSG;

    public PlayerMoveResult() {
    }
    public void okResult(){
        this.isOk = true;
        this.message = OK_MSG;
    }

    public void errorResult(String message){
        this.isOk = false;
        this.message = message;
    }
}
