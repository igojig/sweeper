package ru.igojig.sweeper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Coord {
    private int x;
    private int y;

    private static Coord tmpCoord = new Coord();

    public static Coord getTmpCoord(int x, int y) {
        tmpCoord.setX(x);
        tmpCoord.setY(y);
        return tmpCoord;
    }

    public static Coord getRandomCoord(int boundX, int boundY) {
        return new Coord(ThreadLocalRandom.current().nextInt(boundX), ThreadLocalRandom.current().nextInt(boundY));
    }

    public List<Coord> getNearCoord() {
        List<Coord> list = new ArrayList<>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (!(dx == 0 & dy == 0)) {
                    list.add(new Coord(x + dx, y + dy));
                }
            }
        }
        return list;
    }
}
