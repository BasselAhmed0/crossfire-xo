// src/app/services/game.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Game, GameCreateRequest } from '../models/game.model';
import { MoveRequest, MoveResponse } from '../models/move.model';
import { environment } from '../../enviroment/enviroment';

@Injectable({
  providedIn: 'root',
})
export class GameService {
  private apiUrl = `${environment.apiUrl}/games`;

  constructor(private http: HttpClient) {}

  getGameById(id: number): Observable<Game> {
    return this.http.get<Game>(`${this.apiUrl}/${id}`);
  }

  makeMove(id: number, move: MoveRequest): Observable<MoveResponse> {
    return this.http.post<MoveResponse>(`${this.apiUrl}/${id}/move`, move, {
      headers: { 'Content-Type': 'application/json' },
    });
  }

  // Get all games
  getAllGames(): Observable<Game[]> {
    return this.http.get<Game[]>(this.apiUrl);
  }

  // Get game by ID
  // getGameById(id: number): Observable<Game> {
  //   return this.http.get<Game>(`${this.apiUrl}/${id}`);
  // }

  // Create a new game ⭐ THIS METHOD
  createGame(request: GameCreateRequest): Observable<Game> {
    return this.http.post<Game>(this.apiUrl, request);
  }

  // Make a move
  // makeMove(gameId: number, move: MoveRequest): Observable<MoveResponse> {
  //   return this.http.post<MoveResponse>(`${this.apiUrl}/${gameId}/move`, move);
  // }

  // Get games by player
  getPlayerGames(playerId: number): Observable<Game[]> {
    return this.http.get<Game[]>(`${this.apiUrl}/player/${playerId}`);
  }

  // Delete a game
  deleteGame(gameId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${gameId}`);
  }
}
