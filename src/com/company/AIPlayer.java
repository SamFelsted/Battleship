package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class AIPlayer {
    String name;
    int shipCount;
    ArrayList<Ship> ships = new ArrayList<>();

    Square[][] selfBoard;
    Square[][] guessBoard;

    AIPlayer(String name, int[] ships) {
        this.selfBoard = new Square[10][10];
        this.guessBoard = new Square[10][10];
        this.name = name;
        this.shipCount = ships.length;
        selfBoard = setupBoard(selfBoard);
        guessBoard = setupGuessBoard(guessBoard);
        printBoard();

        setupShips(ships);

    }

    Square[][] setupBoard(Square[][] board){
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++){
                board[x][y] = new Square(SquareType.WATER);
            }
        }
        return board;
    }

    Square[][] setupGuessBoard(Square[][] board){
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++){
                board[x][y] = new Square(SquareType.UNKNOWN);
            }
        }
        return board;
    }

    void setupShips(int[] shipSizes) {

        int i = 0;
        while (i < shipCount) {
            int x = (int) (Math.random() * 10);
            int y = (int) (Math.random() * 10);
            int d = (int) (Math.random() * 5);

            Direction dir;
            switch (d) {
                case 1:
                    dir = Direction.UP;
                    break;
                case 3:
                    dir = Direction.LEFT;
                    break;
                case 4:
                    dir = Direction.RIGHT;
                    break;
                default:
                    dir = Direction.DOWN;
                    break;
            }

            System.out.println("Size " + shipSizes[i] + " : " + x + " : " + y);

            if (placeShip(shipSizes[i], new int[]{x, y}, dir)) {
                i++;
            }
        }
    }


    boolean placeShip(int size, int[] origin, Direction direction){
        if (checkShipSpace(size, origin, direction)) {

            if (direction.equals(Direction.UP)) {
                for (int i = 0; i < size; i++) {
                    if (selfBoard[origin[0]][origin[1] - i].getType().equals(SquareType.SHIP)) return false;
                    else selfBoard[origin[0]][origin[1] - i].setType(SquareType.SHIP);
                }
            }
            else if (direction.equals(Direction.DOWN)) {
                for (int i = 0; i < size; i++) {
                    if (selfBoard[origin[0]][origin[1] + i].getType().equals(SquareType.SHIP)) return false;
                    else selfBoard[origin[0]][origin[1] + i].setType(SquareType.SHIP);
                }
            }
            else if (direction.equals(Direction.LEFT)) {
                for (int i = 0; i < size; i++) {
                    if (selfBoard[origin[0] - i][origin[1]].getType().equals(SquareType.SHIP)) return false;
                    else selfBoard[origin[0] - i][origin[1]].setType(SquareType.SHIP);
                }
            }

            else if (direction.equals(Direction.RIGHT)) {
                for (int i = 0; i < size; i++) {
                    if (selfBoard[origin[0] + i][origin[1]].getType().equals(SquareType.SHIP)) return false;
                    else selfBoard[origin[0] + i][origin[1]].setType(SquareType.SHIP);
                }
            }

            Ship ship = new Ship(size, origin, direction);
            ships.add(ship);
            return true;
        } else return false;
    }

    boolean checkShipSpace(int size, int[] origin, Direction direction){
        if (direction.equals(Direction.DOWN)) return (origin[1] + size <= 10);
        else if (direction.equals(Direction.UP)) return (origin[1] - size >= 0);
        else if (direction.equals(Direction.RIGHT)) return (origin[0] + size <= 10);
        else if (direction.equals(Direction.LEFT)) return (origin[0] - size >= 0);
        else return false;
    }


    SquareType getGuess(int[] guess){
        return selfBoard[guess[0]][guess[1]].getType();
    }

    boolean checkLoss(){
        int count = 0;
        for (Ship ship : ships) {
            if (ship.getStatus().equals(Status.SUNK)) count++;
        }
        return count >= shipCount;
    }

    void confirmGuess(int[] guess, SquareType guessType){
        guessBoard[guess[0]][guess[1]].setType(guessType);
    }

    void hitShip(int[] guess){
        if (selfBoard[guess[0]][guess[1]].getType().equals(SquareType.SHIP)) {
            selfBoard[guess[0]][guess[1]].setType(SquareType.HIT);
            for (Ship ship : ships){
                if (ship.hasSegment(guess)) {
                    ship.killSegment(guess);
                    if (ship.getStatus().equals(Status.SUNK)){
                        System.out.println("A ship has been sunk");
                    }
                }
            }
        }
    }

    int[] guess(){
        int x, y;
        Scanner read = new Scanner(System.in);
        System.out.println("Guess?");
        System.out.println("X: ");
        x = read.nextInt();
        System.out.println("Y: ");
        y = read.nextInt();
        return new int[]{x, y};
    }

    void printBoard(){
        printBoard(selfBoard);
    }
    void printGuessBoard(){
        printBoard(guessBoard);
    }

    void printBoard(Square[][] board){
        System.out.print("  0 1 2 3 4 5 6 7 8 9\n");
        for (int x = 0; x < board.length; x++) {
            System.out.print(x);
            for (Square[] squares : board) {
                if (squares[x].getType().equals(SquareType.WATER)) System.out.print(" -");
                else if (squares[x].getType().equals(SquareType.SHIP)) System.out.print(" Q");
                else if (squares[x].getType().equals(SquareType.HIT)) System.out.print(" *");

            }
            System.out.print("\n");
        }
    }
}

