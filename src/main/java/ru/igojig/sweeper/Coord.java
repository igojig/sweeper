package ru.igojig.sweeper;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Coord {
    private int x;
    private int y;

    @Getter
    private static  List<Coord> gameField;

    public static void initCoords(){
        gameField =new ArrayList<>();
        for(int x = 0; x<Game.getColsX(); x++)
            for(int y = 0; y<Game.getRowsY(); y++)
                gameField.add(new Coord(x,y));
    }

    public static Coord getRandomCoord(int boundX, int boundY) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        int x=current.nextInt(boundX);
        int y=current.nextInt(boundY);
        return getCoordByXY(x,y).orElseThrow();
    }

    public List<Coord> getNearCoords() {
        List<Coord> list = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (!(dx == 0 & dy == 0)) {
                    getCoordByXY(x + dx, y + dy).ifPresent(list::add);
                }
            }
        }
        return list;
    }

    public static Optional<Coord> getCoordByXY(int x, int y){
        return gameField.stream()
                .filter(c -> c.getX() == x && c.getY() == y)
                .findFirst();
    }
}
