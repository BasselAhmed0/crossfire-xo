import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PlayerService } from '../../services/player.service';
import { Router } from '@angular/router';
import { GameService } from '../../services/game.service';
import { Game, GameCreateRequest } from '../../models/game.model';

@Component({
  selector: 'app-home',
  standalone: true,

  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent {
  player1Name: string = '';
  player2Name: string = '';
  loading: boolean = false;
  error: string = '';

  constructor(
    private playerService: PlayerService,
    private gameService: GameService,
    private router: Router
  ) {}

  startGame(): void {
    if (!this.player1Name.trim() || !this.player2Name.trim()) {
      this.error = 'Please enter both player names';
      return;
    }

    this.loading = true;
    this.error = '';

    // Create Player 1
    this.playerService.createPlayer(this.player1Name).subscribe({
      next: (player1) => {
        // Create Player 2
        this.playerService.createPlayer(this.player2Name).subscribe({
          next: (player2) => {
            // Create Game
            const gameRequest: GameCreateRequest = {
              player1Id: player1.id,
              player2Id: player2.id,
              gameMode: 'PVP',
            };

            this.gameService.createGame(gameRequest).subscribe({
              next: (game: Game) => {
                this.loading = false;
                // Navigate to game page
                this.router.navigate(['/game', game.id]);
              },
              error: (err: any) => {
                this.error = 'Failed to create game';
                this.loading = false;
                console.error(err);
              },
            });
          },
          error: (err: any) => {
            this.error = 'Failed to create Player 2';
            this.loading = false;
            console.error(err);
          },
        });
      },
      error: (err: any) => {
        this.error = 'Failed to create Player 1';
        this.loading = false;
        console.error(err);
      },
    });
  }
}
