package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    String name;
    int shipCount;
    ArrayList<Ship> ships = new ArrayList<>();

    Square[][] selfBoard = new Square[10][10];
    Square[][] guessBoard = new Square[10][10];

    Player(String name, int[] ships) {
        this.name = name;
        this.shipCount = ships.length;
        setupBoard(selfBoard);
        setupGuessBoard(guessBoard);
        printBoard();

        setupShips(ships);
        printBoard();
    }

    void setupBoard(Square[][] board){
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++){
                board[x][y] = new Square(SquareType.WATER);
            }
        }
    }

    void setupGuessBoard(Square[][] board){
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++){
                board[x][y] = new Square(SquareType.UNKNOWN);
            }
        }
    }

    void setupShips(int[] shipSizes){
        Scanner read = new Scanner(System.in);
        int i = 0;
        while (i < shipCount) {
            System.out.println("You are about to place a ship with size: " + shipSizes[i] + ", where would you like it?");
            System.out.println("X: ");
            int x = read.nextInt();
            System.out.println("Y: ");
            int y = read.nextInt();
            System.out.println("What direction:\n\t1: UP\n\t2: DOWN\n\t3: LEFT\n\t4: RIGHT");
            int d = read.nextInt();
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


            if (placeShip(shipSizes[i], new int[]{x, y}, dir)) {
                i++;
            }
        }

    }

    boolean placeShip(int size, int[] origin, Direction direction){
        if (checkShipSpace(size, origin, direction)) {



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
        return new int[]{0, 0};
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
            for (int y = 0; y < board.length; y++){
                if (board[x][y].getType().equals(SquareType.WATER)) System.out.print(" -");
                else if (board[x][y].getType().equals(SquareType.SHIP)) System.out.print(" =");
                else if (board[x][y].getType().equals(SquareType.HIT)) System.out.print(" *");

            }
            System.out.print("\n");
        }
    }
}
