// src/app/models/game.model.ts

export enum GameStatus {
  ONGOING = 'ONGOING',
  X_WON = 'X_WON',
  O_WON = 'O_WON',
  DRAW = 'DRAW',
}

export interface PlayerSummary {
  id: number;
  name: string;
  wins?: number;
  losses?: number;
  draws?: number;
  createdAt?: string;
}

export interface Game {
  id: number;
  boardState: string; // JSON stringified 2D array
  currentPlayer: 'X' | 'O';
  turnNumber: number;
  crosshairRow: number | null;
  crosshairCol: number | null;
  status: GameStatus;
  winner?: 'X' | 'O' | null;
  player1: PlayerSummary;
  player2?: PlayerSummary | null;
  message?: string;
}
// src/app/models/game.model.ts

export interface GameCreateRequest {
  player1Id: number;
  player2Id: number | null;
  gameMode: 'PVP' | 'PVE';
}
