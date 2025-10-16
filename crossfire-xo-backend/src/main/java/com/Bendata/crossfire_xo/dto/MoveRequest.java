package com.Bendata.crossfire_xo.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoveRequest {

    @NotNull(message = "Row is required")
    @Min(value = 0, message = "Row must be between 0 and 3")
    @Max(value = 3, message = "Row must be between 0 and 3")
    private Integer row;

    @NotNull(message = "Column is required")
    @Min(value = 0, message = "Column must be between 0 and 3")
    @Max(value = 3, message = "Column must be between 0 and 3")
    private Integer col;
}