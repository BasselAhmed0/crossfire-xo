// src/app/services/player.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Player } from '../models/player.model';
import { environment } from '../../enviroment/enviroment';

@Injectable({
  providedIn: 'root',
})
export class PlayerService {
  private apiUrl = `${environment.apiUrl}/players`;

  constructor(private http: HttpClient) {}

  getAllPlayers(): Observable<Player[]> {
    return this.http.get<Player[]>(this.apiUrl);
  }

  getPlayerById(id: number): Observable<Player> {
    return this.http.get<Player>(`${this.apiUrl}/${id}`);
  }

  createPlayer(name: string): Observable<Player> {
    return this.http.post<Player>(this.apiUrl, name, {
      headers: { 'Content-Type': 'application/json' },
    });
  }
}
