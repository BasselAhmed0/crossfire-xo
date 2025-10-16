package com.Bendata.crossfire_xo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "moves")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "move_number", nullable = false)
    private Integer moveNumber;

    @Column(nullable = false, length = 1)
    private String player; // "X" or "O"

    @Column(name = "row_index", nullable = false)
    private Integer row;

    @Column(name = "col_index", nullable = false)
    private Integer col;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
}