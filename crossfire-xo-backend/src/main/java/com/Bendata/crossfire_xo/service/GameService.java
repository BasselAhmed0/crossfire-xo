package com.Bendata.crossfire_xo.service;

import com.Bendata.crossfire_xo.dto.GameRequest;
import com.Bendata.crossfire_xo.exception.GameNotFoundException;
import com.Bendata.crossfire_xo.model.Game;
import com.Bendata.crossfire_xo.model.GameStatus;
import com.Bendata.crossfire_xo.model.Player;
import com.Bendata.crossfire_xo.repository.GameRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private final ObjectMapper objectMapper;

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Transactional
    public Game createGame(GameRequest request) throws JsonProcessingException {
        Player player1 = playerService.getPlayerById(request.getPlayer1Id());
        Player player2 = request.getPlayer2Id() != null ? playerService.getPlayerById(request.getPlayer2Id()) : null;

        // Initialize empty 4x4 board
        String[][] emptyBoard = new String[4][4];
        String boardJson = objectMapper.writeValueAsString(emptyBoard);

        Game game = new Game();
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setBoardState(boardJson);
        game.setTurnNumber(1);
        game.setCurrentPlayer("X");
        game.setStatus(GameStatus.ONGOING);
        game.setCrosshairRow(0);
        game.setCrosshairCol(0);

        return gameRepository.save(game);
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found with id: " + id));
    }

    public List<Game> getPlayerGames(Long playerId) {
        Player player = playerService.getPlayerById(playerId);
        return gameRepository.findByPlayer1OrPlayer2OrderByCreatedAtDesc(player, player);
    }

    @Transactional
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }



    @Transactional
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public String[][] parseBoardState(String boardJson) throws JsonProcessingException {
        return objectMapper.readValue(boardJson, String[][].class);
    }

    public String serializeBoardState(String[][] board) throws JsonProcessingException {
        return objectMapper.writeValueAsString(board);
    }
}