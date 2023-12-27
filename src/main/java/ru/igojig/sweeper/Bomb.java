package ru.igojig.sweeper;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class Bomb {
    private Map<Coordinate, Cell> bombsMap;

    @Getter
    private int totalBombsCount;

    public void start(int bombCount) {
        totalBombsCount = 0;
        bombsMap = new HashMap<>();
        generateBomb(bombCount);
        setAllNumbers();
    }

    private void generateBomb(int bombCount) {
        totalBombsCount = bombCount;
        int count = 0;
        while (count < bombCount) {
            Coordinate randomCoordinate = Coordinate.getRandomCoord(Game.getColsX(), Game.getRowsY());
            if (bombsMap.entrySet().stream().noneMatch(coordCellEntry -> coordCellEntry.getKey().equals(randomCoordinate))) {
                bombsMap.put(randomCoordinate, Cell.BOMB);
                count++;
            }
        }
    }

    private void setAllNumbers() {
        Coordinate.getGameField().stream()
                .filter(c -> bombsMap.get(c) != Cell.BOMB)
                .forEach(c -> {
                    int i = calculateBombAround(c);
                    bombsMap.put(c, Cell.values()[i]);
                });
    }

    public void setBombed(Coordinate coordinate) {
        bombsMap.put(coordinate, Cell.BOMBED);
    }

    private int calculateBombAround(Coordinate coordinate) {
        List<Coordinate> nearCoordinate = coordinate.getNearCoords();

        return (int) nearCoordinate.stream()
                .filter(c -> bombsMap.get(c) == Cell.BOMB)
                .count();
    }

    public Cell getBomb(Coordinate coordinate) {
        return bombsMap.get(coordinate);
    }
}
