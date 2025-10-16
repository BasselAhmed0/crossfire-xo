package com.Bendata.crossfire_xo.config;

import com.Bendata.crossfire_xo.dto.GameRequest;
import com.Bendata.crossfire_xo.service.GameService;
import com.Bendata.crossfire_xo.service.PlayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder {

    private final PlayerService playerService;
    private final GameService gameService;

    @PostConstruct
    public void seed() throws JsonProcessingException {
        var aliceOpt = playerService.findByName("Alice");
        var bobOpt = playerService.findByName("Bob");

        var alice = aliceOpt.orElseGet(() -> playerService.createPlayer("Alice"));
        var bob = bobOpt.orElseGet(() -> playerService.createPlayer("Bob"));

        GameRequest req = new GameRequest();
        req.setPlayer1Id(alice.getId());
        req.setPlayer2Id(bob.getId());
        req.setGameMode("PVP");

        gameService.createGame(req);
    }
}
