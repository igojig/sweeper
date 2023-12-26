package ru.igojig.sweeper;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class Bomb {
    private Map<Coord, Cell> bombsMap;

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
            Coord randomCoord = Coord.getRandomCoord(Game.getColsX(), Game.getRowsY());
            if (bombsMap.entrySet().stream().noneMatch(coordCellEntry -> coordCellEntry.getKey().equals(randomCoord))) {
                bombsMap.put(randomCoord, Cell.BOMB);
                count++;
            }
        }
    }

    private void setAllNumbers() {
        Coord.getGameField().stream()
                .filter(c -> bombsMap.get(c) != Cell.BOMB)
                .forEach(c -> {
                    int i = calculateBombAround(c);
                    bombsMap.put(c, Cell.values()[i]);
                });
    }

    public void setBombed(Coord coord) {
        bombsMap.put(coord, Cell.BOMBED);
    }

    private int calculateBombAround(Coord coord) {
        List<Coord> nearCoord = coord.getNearCoords();

        return (int) nearCoord.stream()
                .filter(c -> bombsMap.get(c) == Cell.BOMB)
                .count();
    }

    public Cell getBomb(Coord coord) {
        return bombsMap.get(coord);
    }
}
