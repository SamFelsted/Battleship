package com.company;

public class Game {

    Player P1;
    AIPlayer P2;

    int round;

    Game(){
        P1 = new Player("Sam", new int[]{3, 3});
        P2 = new AIPlayer("Sam", new int[]{3, 3});
        P2.printBoard();
        round = 0;
        turn();
    }

    void turn(){
        if (round % 2 == 0){
            System.out.println("Player 1's turn");
            int[] guess = P1.guess();
            if (P2.getGuess(guess).equals(SquareType.SHIP) || P2.getGuess(guess).equals(SquareType.WATER)){
                P2.hitShip(guess);
                P1.confirmGuess(guess, P2.getGuess(guess));
            }
            else System.out.println("Not valid");
            if (P2.checkLoss()) {
                System.out.println("Player 1 has won");
                return;
            }
            P1.printGuessBoard();



        }

        turn();

    }


}
