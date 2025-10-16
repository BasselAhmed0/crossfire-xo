package com.Bendata.crossfire_xo.dto;


import com.Bendata.crossfire_xo.model.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {
    private Long gameId;
    private String[][] board;
    private String currentPlayer;
    private Integer turnNumber;
    private Integer crosshairRow;
    private Integer crosshairCol;
    private GameStatus status;
    private String winner;
    private String message;
}