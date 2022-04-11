package com.company;

import java.util.ArrayList;

public class Player {
    String name;
    ArrayList<Ship> ships = new ArrayList<>();

    Square[][] selfBoard = new Square[10][10];
    Square[][] guessBoard = new Square[10][10];

    Player(String name) {
        setupBoard(selfBoard);
        setupBoard(guessBoard);
    }

    void setupBoard(Square[][] board){
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++){
                board[x][y] = new Square(SquareType.WATER);
            }
        }
    }


    boolean placeShip(int size, int[] origin, Direction direction){
        if (checkSpace(size, origin, direction)) {
            Ship ship = new Ship(size, origin, direction);
            ships.add(ship);
            return true;
        } else return false;
    }

    boolean checkSpace(int size, int[] origin, Direction direction){
        if (direction.equals(Direction.DOWN)) return (origin[1] + size <= 10);
        else if (direction.equals(Direction.UP)) return (origin[1] - size >= 0);
        else if (direction.equals(Direction.RIGHT)) return (origin[0] + size <= 10);
        else if (direction.equals(Direction.LEFT)) return (origin[0] - size >= 0);
        else return false;
    }

}
