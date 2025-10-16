package com.Bendata.crossfire_xo.service;

import com.Bendata.crossfire_xo.model.Player;
import com.Bendata.crossfire_xo.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Transactional
    public Player createPlayer(String name) {
        Player player = new Player();
        player.setName(name);
        player.setWins(0);
        player.setLosses(0);
        player.setDraws(0);
        return playerRepository.save(player);
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
    }

    public Optional<Player> findByName(String name) {
        return playerRepository.findByName(name);
    }

    public Iterable<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Transactional
    public void updateWins(Long playerId) {
        Player player = getPlayerById(playerId);
        player.setWins(player.getWins() + 1);
        playerRepository.save(player);
    }

    @Transactional
    public void updateLosses(Long playerId) {
        Player player = getPlayerById(playerId);
        player.setLosses(player.getLosses() + 1);
        playerRepository.save(player);
    }

    @Transactional
    public void updateDraws(Long playerId) {
        Player player = getPlayerById(playerId);
        player.setDraws(player.getDraws() + 1);
        playerRepository.save(player);
    }

}