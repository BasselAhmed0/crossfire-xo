package com.Bendata.crossfire_xo.repository;

import com.Bendata.crossfire_xo.model.Game;
import com.Bendata.crossfire_xo.model.GameStatus;
import com.Bendata.crossfire_xo.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Game> findById(Long id);

    List<Game> findByStatus(GameStatus status);

    @Query("SELECT g FROM Game g WHERE g.player1 = ?1 OR g.player2 = ?1")
    List<Game> findGamesByPlayer(Player player);

    List<Game> findByPlayer1OrPlayer2OrderByCreatedAtDesc(Player player1, Player player2);
}