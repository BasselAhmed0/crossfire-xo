package com.Bendata.crossfire_xo.controller;

import com.Bendata.crossfire_xo.dto.GameRequest;
import com.Bendata.crossfire_xo.dto.MoveRequest;
import com.Bendata.crossfire_xo.dto.MoveResponse;
import com.Bendata.crossfire_xo.model.Game;
import com.Bendata.crossfire_xo.service.GameLogicService;
import com.Bendata.crossfire_xo.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {

    private final GameService gameService;
    private final GameLogicService gameLogicService;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GameController.class);

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody GameRequest request) throws JsonProcessingException {
        Game game = gameService.createGame(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@PathVariable Long id) {
        Game game = gameService.getGameById(id);
        return ResponseEntity.ok(game);
    }

    @PostMapping("/{id}/move")
    public ResponseEntity<MoveResponse> makeMove(
            @PathVariable Long id,
            @Valid @RequestBody MoveRequest moveRequest) throws JsonProcessingException {
        MoveResponse response = gameLogicService.makeMove(id, moveRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Game>> getPlayerGames(@PathVariable Long playerId) {
        List<Game> games = gameService.getPlayerGames(playerId);
        return ResponseEntity.ok(games);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}