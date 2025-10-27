const express = require('express');
const app = express();
app.use(express.json());

const players = [
  { id: 1, name: 'Alice' },
  { id: 2, name: 'Bob' }
];

const games = [
  { id: 1, name: 'Quick Game', players: [1, 2] }
];

app.get('/api/players', (req, res) => {
  res.json(players);
});

app.post('/api/players', (req, res) => {
  const newPlayer = { id: players.length + 1, ...req.body };
  players.push(newPlayer);
  res.status(201).json(newPlayer);
});

app.get('/api/games', (req, res) => {
  res.json(games);
});

app.post('/api/games', (req, res) => {
  const newGame = { id: games.length + 1, ...req.body };
  games.push(newGame);
  res.status(201).json(newGame);
});

app.post('/api/games/:id/move', (req, res) => {
  res.json({ success: true, gameId: parseInt(req.params.id, 10) });
});

const port = process.env.PORT || 8080;
app.listen(port, () => console.log(`Mock API server running on http://localhost:${port}`));
