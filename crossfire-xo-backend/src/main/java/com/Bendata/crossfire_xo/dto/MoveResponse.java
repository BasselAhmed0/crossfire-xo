package com.Bendata.crossfire_xo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveResponse {
    private Boolean success;
    private String message;
    private GameResponse gameState;
}