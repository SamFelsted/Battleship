package com.company;

public class Game {

    Player P1;
    Player P2;

    int round;

    Game(){
        P1 = new Player("Sam", new int[]{3, 3});
        P2 = new Player("Bot",  new int[]{});
        round = 0;
    }

    void turn(){
        if (round % 2 == 0){
            int[] guess = P1.guess();
            if (P2.getGuess(guess).equals(SquareType.SHIP) || P2.getGuess(guess).equals(SquareType.UNKNOWN)){
                P2.hitShip(guess);
                P1.confirmGuess(guess, P2.getGuess(guess));
            }
            else System.out.println("Not valid");
            if (P2.checkLoss()) {
                System.out.println("Player 1 has won");
                return;
            }
        }

        turn();

    }


}
