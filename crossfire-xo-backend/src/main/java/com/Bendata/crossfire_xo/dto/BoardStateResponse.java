package com.Bendata.crossfire_xo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardStateResponse {
    private String[][] board;
    private Integer crosshairRow;
    private Integer crosshairCol;
    private String currentPlayer;
    private boolean[] blockedRows;  // size 4
    private boolean[] blockedCols;  // size 4
}