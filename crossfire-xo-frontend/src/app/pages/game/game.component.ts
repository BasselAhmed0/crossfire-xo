import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { Game, GameStatus, PlayerSummary } from '../../models/game.model';
import { GameService } from '../../services/game.service';
import { MoveRequest, MoveResponse } from '../../models/move.model';

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss'],
})
export class GameComponent implements OnInit {
  game: Game | null = null;
  board: (string | null)[][] = Array.from({ length: 4 }, () =>
    Array.from({ length: 4 }, () => null)
  );
  gameId: number = 0;
  loading: boolean = false;
  error: string = '';
  message: string = '';
  GameStatus = GameStatus; // Make enum available in template

  constructor(
    private gameService: GameService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.gameId = +params['id'];
      this.loadGame();
    });
  }

  trackByIndex(index: number): number {
    return index;
  }

  loadGame(): void {
    this.loading = true;
    this.gameService.getGameById(this.gameId).subscribe({
      next: (game) => {
        this.game = game;
        this.board = JSON.parse(game.boardState);
        this.loading = false;
        this.checkGameStatus();
      },
      error: (err) => {
        this.error = 'Failed to load game';
        this.loading = false;
        console.error(err);
      },
    });
  }

  makeMove(row: number, col: number): void {
    if (!this.game || this.game.status !== GameStatus.ONGOING) {
      return;
    }

    // Check if cell is blocked
    if (this.isCellBlocked(row, col)) {
      this.message = 'This cell is blocked by the crosshair!';
      setTimeout(() => (this.message = ''), 2000);
      return;
    }

    const move: MoveRequest = { row, col };

    this.gameService.makeMove(this.gameId, move).subscribe({
      next: (response) => {
        if (this.game) {
          // Update the game state from the response
          this.game.boardState = JSON.stringify(response.gameState.board);
          this.game.currentPlayer = response.gameState.currentPlayer;
          this.game.turnNumber = response.gameState.turnNumber;
          this.game.crosshairRow = response.gameState.crosshairRow;
          this.game.crosshairCol = response.gameState.crosshairCol;
          this.game.status = response.gameState.status as GameStatus;
          this.game.winner = response.gameState.winner;
          this.game.message = response.message;

          // Update the local board representation
          this.board = response.gameState.board;

          // Check for win/draw messages
          this.checkGameStatus();
        }
      },
      error: (err) => {
        this.error = err.error.message || 'Failed to make move';
        console.error(err);
      },
    });
  }

  isCellBlocked(row: number, col: number): boolean {
    if (!this.game) return false;
    return row === this.game.crosshairRow || col === this.game.crosshairCol;
  }

  getCellClass(row: number, col: number): string {
    const classes = ['cell'];

    if (this.board[row][col]) {
      classes.push('occupied');
      classes.push(this.board[row][col] === 'X' ? 'cell-x' : 'cell-o');
    }

    if (this.isCellBlocked(row, col)) {
      classes.push('blocked');
    }

    return classes.join(' ');
  }

  checkGameStatus(): void {
    if (!this.game) return;

    if (this.game.status === GameStatus.X_WON) {
      this.message = `🎉 Player X (${this.game.player1.name}) wins!`;
    } else if (this.game.status === GameStatus.O_WON) {
      this.message = `🎉 Player O (${this.game.player2?.name || 'AI'}) wins!`;
    } else if (this.game.status === GameStatus.DRAW) {
      this.message = this.game.message || "It's a draw!";
    }
  }

  newGame(): void {
    this.router.navigate(['/']);
  }

  quit(): void {
    if (confirm('Are you sure you want to quit this game?')) {
      this.router.navigate(['/']);
    }
  }
}
