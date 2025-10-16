package com.Bendata.crossfire_xo.util;


import com.Bendata.crossfire_xo.model.Game;
import org.springframework.stereotype.Component;

@Component
public class GameValidator {

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 4 && col >= 0 && col < 4;
    }

    public boolean isCellEmpty(String[][] board, int row, int col) {
        return board[row][col] == null || board[row][col].isEmpty();
    }

    public boolean isCellBlocked(int row, int col, int crosshairRow, int crosshairCol) {
        return row == crosshairRow || col == crosshairCol;
    }

    public boolean isPlayerTurn(Game game, String player) {
        return game.getCurrentPlayer().equals(player);
    }
}