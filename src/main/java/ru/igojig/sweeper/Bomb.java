package ru.igojig.sweeper;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor
public class Bomb {
    private Range ranges;
    private Map<Coord, Cell> bombsMap;

    @Getter
    private int totalBombsCount;

    public Bomb(Range ranges) {
        this.ranges = ranges;
    }

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
            Coord randomCoord = Coord.getRandomCoord(Game.X_SIZE, Game.Y_SIZE);

            if (bombsMap.entrySet().stream().noneMatch(coordCellEntry -> coordCellEntry.getKey().equals(randomCoord))) {
                bombsMap.put(randomCoord, Cell.BOMB);
                count++;
            }
        }
    }

    private void setAllNumbers() {
        ranges.getCoordArrayList().forEach(coord -> {
            if (bombsMap.get(coord) != Cell.BOMB) {
                int i = calculateBombAround(coord);
                bombsMap.put(coord, Cell.values()[i]);
            }
        });
    }

    public void setBombed(Coord coord) {
        bombsMap.put(coord, Cell.BOMBED);
    }

    private int calculateBombAround(Coord coord) {
        AtomicInteger count = new AtomicInteger();
        List<Coord> nearCoord = coord.getNearCoord();

        nearCoord.forEach(c -> {
            if (bombsMap.get(c) == Cell.BOMB) {
                count.getAndIncrement();
            }
        });
        return count.get();
    }

    public Cell getBomb(Coord coord) {
        return bombsMap.get(coord);
    }
}
