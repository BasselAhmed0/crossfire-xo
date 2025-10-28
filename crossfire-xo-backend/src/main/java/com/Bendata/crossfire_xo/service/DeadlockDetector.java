package com.Bendata.crossfire_xo.service;

import org.springframework.stereotype.Service;

@Service
public class DeadlockDetector {

    public boolean isDeadlocked(String[][] board, int crosshairRow, int crosshairCol) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (isCellEmpty(board, row, col) && !isCellBlocked(row, col, crosshairRow, crosshairCol)) {
                    return false; // Found a valid move
                }
            }
        }
        return true; // No valid moves found
    }

    private boolean isCellEmpty(String[][] board, int row, int col) {
        return board[row][col] == null || board[row][col].isEmpty();
    }

    private boolean isCellBlocked(int row, int col, int crosshairRow, int crosshairCol) {
        return row == crosshairRow || col == crosshairCol;
    }
}
