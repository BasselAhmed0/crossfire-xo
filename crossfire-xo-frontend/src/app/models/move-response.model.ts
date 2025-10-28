// src/app/models/game-response.model.ts

import { GameStateResponse } from './move.model';

export interface GameResponse {
  message: string;
  gameState: GameStateResponse;
}
