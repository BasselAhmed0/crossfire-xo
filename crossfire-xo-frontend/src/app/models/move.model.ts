// src/app/models/move.model.ts

export interface MoveRequest {
  row: number;
  col: number;
}

export interface GameStateResponse {
  board: (string | null)[][];
  currentPlayer: 'X' | 'O';
  turnNumber: number;
  crosshairRow: number | null;
  crosshairCol: number | null;
  status: string;
  winner?: 'X' | 'O' | null;
}
