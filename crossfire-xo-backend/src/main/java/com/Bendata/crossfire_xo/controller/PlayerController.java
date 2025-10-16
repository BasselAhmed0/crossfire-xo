package com.Bendata.crossfire_xo.controller;

import com.Bendata.crossfire_xo.model.Player;
import com.Bendata.crossfire_xo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody String name) {
        Player player = playerService.createPlayer(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(player);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        Player player = playerService.getPlayerById(id);
        return ResponseEntity.ok(player);
    }

    @GetMapping
    public ResponseEntity<Iterable<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

}
