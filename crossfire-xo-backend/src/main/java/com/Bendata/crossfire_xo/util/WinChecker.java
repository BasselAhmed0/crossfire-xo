package com.Bendata.crossfire_xo.util;


import org.springframework.stereotype.Component;

@Component
public class WinChecker {

    private static final int BOARD_SIZE = 4;
    private static final int WIN_LENGTH = 4;

    public String checkWinner(String[][] board) {
        // Check horizontal
        String horizontalWinner = checkHorizontal(board);
        if (horizontalWinner != null) return horizontalWinner;

        // Check vertical
        String verticalWinner = checkVertical(board);
        if (verticalWinner != null) return verticalWinner;

        // Check diagonals
        String diagonalWinner = checkDiagonals(board);
        if (diagonalWinner != null) return diagonalWinner;

        return null;
    }

    private String checkHorizontal(String[][] board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (board[row][0] != null &&
                    !board[row][0].isEmpty() &&
                    board[row][0].equals(board[row][1]) &&
                    board[row][1].equals(board[row][2]) &&
                    board[row][2].equals(board[row][3])) {
                return board[row][0];
            }
        }
        return null;
    }

    private String checkVertical(String[][] board) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (board[0][col] != null &&
                    !board[0][col].isEmpty() &&
                    board[0][col].equals(board[1][col]) &&
                    board[1][col].equals(board[2][col]) &&
                    board[2][col].equals(board[3][col])) {
                return board[0][col];
            }
        }
        return null;
    }

    private String checkDiagonals(String[][] board) {
        // Main diagonal (top-left to bottom-right)
        if (board[0][0] != null &&
                !board[0][0].isEmpty() &&
                board[0][0].equals(board[1][1]) &&
                board[1][1].equals(board[2][2]) &&
                board[2][2].equals(board[3][3])) {
            return board[0][0];
        }

        // Anti-diagonal (top-right to bottom-left)
        if (board[0][3] != null &&
                !board[0][3].isEmpty() &&
                board[0][3].equals(board[1][2]) &&
                board[1][2].equals(board[2][1]) &&
                board[2][1].equals(board[3][0])) {
            return board[0][3];
        }

        return null;
    }

    public boolean isBoardFull(String[][] board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == null || board[row][col].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}