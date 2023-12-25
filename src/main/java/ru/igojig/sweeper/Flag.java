package ru.igojig.sweeper;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class Flag {

    //    private Cell[][] flags;
    private Range ranges;

    private Map<Coord, Cell> flagsMap;

    public Flag(Range ranges) {
        this.ranges = ranges;
    }

    @Getter
    private int flagCount;

    void start() {
//        flags = new Cell[Game.X_SIZE][Game.Y_SIZE];
        flagsMap = new HashMap<>();
        initFlag();
        flagCount = 0;
    }

    void initFlag() {

        ranges.getCoordArrayList().forEach(coord -> flagsMap.put(coord, Cell.CLOSED));

//        for (int x = 0; x < Game.X_SIZE; x++)
//            for (int y = 0; y < Game.Y_SIZE; y++)
//                flags[x][y] = Cell.CLOSED;
    }

    public void switchFlag(Coord coord) {
        switch (flagsMap.get(coord)) {
            case CLOSED -> {
//                flags[x][y] = Cell.FLAGGED;
                flagsMap.put(coord, Cell.FLAGGED);
                flagCount++;
            }
            case FLAGGED -> {
//                flags[x][y] = Cell.INFORM;
                flagsMap.put(coord, Cell.INFORM);
                flagCount--;
            }
            case INFORM -> flagsMap.put(coord, Cell.CLOSED);
        }
    }

    public Cell getFlags(Coord coord) {
        return flagsMap.get(coord);
    }

    public void setFlags(Coord coord, Cell cell) {

        flagsMap.put(coord, cell);
//        flags[x][y] = cell;
    }

//    public int getFlagCount(){
//        return flagCount;
//    }
}
