package ru.igojig.sweeper;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class Flag {

    private Map<Coordinate, Cell> flagsMap;

    @Getter
    private int flagCount;

    void start() {
        flagsMap = new HashMap<>();
        initFlag();
        flagCount = 0;
    }

    void initFlag() {
        Coordinate.getGameField().forEach(coord -> flagsMap.put(coord, Cell.CLOSED));
    }

    public void switchFlag(Coordinate coordinate) {
        switch (flagsMap.get(coordinate)) {
            case CLOSED -> {
                flagsMap.put(coordinate, Cell.FLAGGED);
                flagCount++;
            }
            case FLAGGED -> {
                flagsMap.put(coordinate, Cell.INFORM);
                flagCount--;
            }
            case INFORM -> flagsMap.put(coordinate, Cell.CLOSED);
        }
    }

    public Cell getFlags(Coordinate coordinate) {
        return flagsMap.get(coordinate);
    }

    public void setFlags(Coordinate coordinate, Cell cell) {
        flagsMap.put(coordinate, cell);
    }
}
