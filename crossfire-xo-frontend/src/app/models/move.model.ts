// src/app/models/move.model.ts

export interface MoveRequest {
  row: number;
  col: number;
}

export interface GameState {
  board: (string | null)[][];
  currentPlayer: 'X' | 'O';
  turnNumber: number;
  crosshairRow: number | null;
  crosshairCol: number | null;
  status: string;
  winner?: 'X' | 'O' | null;
}

export interface MoveResponse {
  success: boolean;
  message: string;
  gameState: GameState;
}
