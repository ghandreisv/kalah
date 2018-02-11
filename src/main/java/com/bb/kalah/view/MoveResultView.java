package com.bb.kalah.view;

import lombok.Data;

@Data
public class MoveResultView {

    private GameSessionView gameSessionView;
    private boolean isSuccesful;
    private String message;

}
