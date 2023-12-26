package ru.igojig.sweeper;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class Flag {

    private Map<Coord, Cell> flagsMap;

    @Getter
    private int flagCount;

    void start() {
        flagsMap = new HashMap<>();
        initFlag();
        flagCount = 0;
    }

    void initFlag() {
        Coord.getGameField().forEach(coord -> flagsMap.put(coord, Cell.CLOSED));
    }

    public void switchFlag(Coord coord) {
        switch (flagsMap.get(coord)) {
            case CLOSED -> {
                flagsMap.put(coord, Cell.FLAGGED);
                flagCount++;
            }
            case FLAGGED -> {
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
    }
}
