package com.company;

import java.util.ArrayList;

public class Ship {
    private Status status;
    ArrayList<Integer[]> segments = new ArrayList<>();

    Ship(int size, int[] origin, Direction direction){
        status = Status.ACTIVE;

        for (int i = 0; i < size; i++) {
            if (direction.equals(Direction.DOWN)) segments.add(new Integer[]{origin[0], origin[1] + i} );
            else if (direction.equals(Direction.UP)) segments.add(new Integer[]{origin[0], origin[1] - i} );
            else if (direction.equals(Direction.RIGHT)) segments.add(new Integer[]{origin[0] - i, origin[1]} );
            else if (direction.equals(Direction.LEFT)) segments.add(new Integer[]{origin[0] + i, origin[1]} );
        }
    }

    boolean hasSegment(int[] pos){
        for (Integer[] segment : segments) {
            if (segment[0] == pos[0] && segment[1] == pos[1] == true) return true;
        }
        return false;
    }

    public Status getStatus() {
        return status;
    }
}
