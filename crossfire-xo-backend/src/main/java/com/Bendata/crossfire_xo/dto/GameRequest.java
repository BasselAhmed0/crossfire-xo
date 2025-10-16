package com.Bendata.crossfire_xo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRequest {
    private Long player1Id;
    private Long player2Id;  // null for AI opponent
    private String gameMode; // "PVP" or "PVE"
}