package com.Bendata.crossfire_xo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player1_id", nullable = false)
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2_id")
    private Player player2;  // null if playing vs AI

    @Column(name = "board_state", nullable = false, columnDefinition = "TEXT")
    private String boardState;  // JSON: [["X","O",null,...], ...]

    @Column(name = "turn_number", nullable = false)
    private Integer turnNumber = 1;

    @Column(name = "current_player", nullable = false, length = 1)
    private String currentPlayer = "X";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GameStatus status = GameStatus.ONGOING;

    @Column(name = "crosshair_row", nullable = false)
    private Integer crosshairRow = 0;

    @Column(name = "crosshair_col", nullable = false)
    private Integer crosshairCol = 0;

    @Column(length = 1)
    private String winner;  // "X", "O", or null

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}