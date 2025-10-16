// src/app/models/player.model.ts

export interface Player {
  id: number;
  name: string;
  wins: number;
  losses: number;
  draws: number;
  createdAt: string;
}

export interface PlayerCreateRequest {
  name: string;
}
