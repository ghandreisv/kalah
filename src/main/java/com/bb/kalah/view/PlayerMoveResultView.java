package com.bb.kalah.view;

import lombok.Data;

@Data
public class PlayerMoveResultView {

    private GameSessionView gameSessionView;
    private Boolean isSuccesful;
    private String message;

}
