package com.Bendata.crossfire_xo.service;

import com.Bendata.crossfire_xo.dto.GameResponse;
import com.Bendata.crossfire_xo.dto.MoveRequest;
import com.Bendata.crossfire_xo.dto.MoveResponse;
import com.Bendata.crossfire_xo.exception.InvalidMoveException;
import com.Bendata.crossfire_xo.model.Game;
import com.Bendata.crossfire_xo.model.GameStatus;
import com.Bendata.crossfire_xo.model.Move;
import com.Bendata.crossfire_xo.repository.MoveRepository;
import com.Bendata.crossfire_xo.util.GameValidator;
import com.Bendata.crossfire_xo.util.WinChecker;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GameLogicService {

    private final GameService gameService;
    private final PlayerService playerService;
    private final MoveRepository moveRepository;
    private final GameValidator validator;
    private final WinChecker winChecker;

    @Transactional
    public MoveResponse makeMove(Long gameId, MoveRequest moveRequest) throws JsonProcessingException {
        Game game = gameService.getGameById(gameId);

        // Validate game is ongoing
        if (game.getStatus() != GameStatus.ONGOING) {
            throw new InvalidMoveException("Game has already ended");
        }

        int row = moveRequest.getRow();
        int col = moveRequest.getCol();

        // Parse board
        String[][] board = gameService.parseBoardState(game.getBoardState());

        // Validate move
        validateMove(game, board, row, col);

        // Place move
        board[row][col] = game.getCurrentPlayer();

        // Save move to history
        saveMove(game, row, col);

        // Check for winner
        String winner = winChecker.checkWinner(board);

        if (winner != null) {
            // We have a winner!
            game.setStatus(winner.equals("X") ? GameStatus.X_WON : GameStatus.O_WON);
            game.setWinner(winner);
            updatePlayerStats(game, winner);
        } else if (winChecker.isBoardFull(board)) {
            // Draw
            game.setStatus(GameStatus.DRAW);
            updatePlayerStatsForDraw(game);
        } else {
            // Continue game
            switchPlayer(game);
            game.setTurnNumber(game.getTurnNumber() + 1);
            updateCrosshairPosition(game);
        }

        // Save updated board
        game.setBoardState(gameService.serializeBoardState(board));
        gameService.saveGame(game);

        // Build response
        GameResponse gameResponse = buildGameResponse(game, board);

        return MoveResponse.builder()
                .success(true)
                .message(winner != null ? winner + " wins!" : "Move successful")
                .gameState(gameResponse)
                .build();
    }

    private void validateMove(Game game, String[][] board, int row, int col) {
        // Check position validity
        if (!validator.isValidPosition(row, col)) {
            throw new InvalidMoveException("Invalid position");
        }

        // Check if cell is empty
        if (!validator.isCellEmpty(board, row, col)) {
            throw new InvalidMoveException("Cell is already occupied");
        }

        // Check if cell is blocked by crosshair
        if (validator.isCellBlocked(row, col, game.getCrosshairRow(), game.getCrosshairCol())) {
            throw new InvalidMoveException("Cell is blocked by crosshair");
        }
    }

    private void updateCrosshairPosition(Game game) {
        // Crosshair moves diagonally: (0,0) -> (1,1) -> (2,2) -> (3,3) -> (0,0)
        // int position = (game.getTurnNumber() - 1) % 4;
        // game.setCrosshairRow(position);
        // game.setCrosshairCol(position);

        int position = (game.getTurnNumber() - 1) / 2 % 4;
        game.setCrosshairRow(position);
        game.setCrosshairCol(position);
    }

    private void switchPlayer(Game game) {
        game.setCurrentPlayer(game.getCurrentPlayer().equals("X") ? "O" : "X");
    }

    private void saveMove(Game game, int row, int col) {
        Move move = new Move();
        move.setGame(game);
        move.setMoveNumber(game.getTurnNumber());
        move.setPlayer(game.getCurrentPlayer());
        move.setRow(row);
        move.setCol(col);
        moveRepository.save(move);
    }

    private void updatePlayerStats(Game game, String winner) {
        if (winner.equals("X")) {
            playerService.updateWins(game.getPlayer1().getId());
            if (game.getPlayer2() != null) {
                playerService.updateLosses(game.getPlayer2().getId());
            }
        } else {
            if (game.getPlayer2() != null) {
                playerService.updateWins(game.getPlayer2().getId());
            }
            playerService.updateLosses(game.getPlayer1().getId());
        }
    }

    private void updatePlayerStatsForDraw(Game game) {
        playerService.updateDraws(game.getPlayer1().getId());
        if (game.getPlayer2() != null) {
            playerService.updateDraws(game.getPlayer2().getId());
        }
    }

    private GameResponse buildGameResponse(Game game, String[][] board) {
        return GameResponse.builder()
                .gameId(game.getId())
                .board(board)
                .currentPlayer(game.getCurrentPlayer())
                .turnNumber(game.getTurnNumber())
                .crosshairRow(game.getCrosshairRow())
                .crosshairCol(game.getCrosshairCol())
                .status(game.getStatus())
                .winner(game.getWinner())
                .build();
    }
}