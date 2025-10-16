# Crossfire XO Backend

A Spring Boot REST API for the Crossfire XO game - a strategic 4x4 Tic-Tac-Toe variant with a moving crosshair blocker.

## Features
- 4x4 game board
- Moving crosshair that blocks row + column
- Player vs Player mode
- Win detection (4 in a row)
- Player statistics tracking
- Move history

## Technologies
- Java 17
- Spring Boot 3.2
- Spring Data JPA
- MySQL
- Lombok
- Maven

## Setup

### Prerequisites
- JDK 17+
- MySQL 8.0+
- Maven 3.8+

### Database Setup
```sql
CREATE DATABASE crossfirexo;