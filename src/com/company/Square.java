package com.company;

public class Square {
    private SquareType type;

    Square(SquareType type){
        this.type = type;
    }

    public SquareType getType() {
        return type;
    }

    public void setType(SquareType type) {
        this.type = type;
    }
}
