package com.Bendata.crossfire_xo.repository;

import com.Bendata.crossfire_xo.model.Game;
import com.Bendata.crossfire_xo.model.GameStatus;
import com.Bendata.crossfire_xo.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByStatus(GameStatus status);

    @Query("SELECT g FROM Game g WHERE g.player1 = ?1 OR g.player2 = ?1")
    List<Game> findGamesByPlayer(Player player);

    List<Game> findByPlayer1OrPlayer2OrderByCreatedAtDesc(Player player1, Player player2);
}