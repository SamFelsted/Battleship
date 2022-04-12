package com.company;

public class Game {

    Player P1;
    AIPlayer P2;

    int round;

    Game(){
        P1 = new Player("Sam", new int[]{3, 3});
        P2 = new AIPlayer("Sam", new int[]{3, 3});
        round = 0;
        turn();
    }

    void turn(){
        if (round % 2 == 0){
            System.out.println("\n***Player 1's turn***\n");
            int[] guess = P1.guess();
            if (P2.getGuess(guess).equals(SquareType.SHIP) || P2.getGuess(guess).equals(SquareType.WATER)){
                P2.hitShip(guess);
                P1.confirmGuess(guess, P2.getGuess(guess));
                round++;
            }
            else System.out.println("Not valid");
            if (P2.checkLoss()) {
                System.out.println("\n***Player 1 has won***\n");
                return;
            }
            P1.printGuessBoard();
        }  else {
            System.out.println("\n***Player 2's turn***\n");
            int[] guess = P2.guess();
            if (P1.getGuess(guess).equals(SquareType.SHIP) || P1.getGuess(guess).equals(SquareType.WATER)){
                P1.hitShip(guess);
                P2.confirmGuess(guess, P1.getGuess(guess));
                round++;
            }
            if (P1.checkLoss()) {
                System.out.println("\n***Player 2 has won***\n");
                return;
            }
            P2.printGuessBoard();
        }

        turn();
    }


}
