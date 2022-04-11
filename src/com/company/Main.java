package com.company;

public class Main {

    public static void main(String[] args) {
        Player sam = new Player("Sam");
        System.out.println(sam.placeShip(1, new int[]{0, 0}, Direction.RIGHT));
    }
}
