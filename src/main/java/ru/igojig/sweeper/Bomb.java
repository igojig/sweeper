package ru.igojig.sweeper;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor
public class Bomb {
    //    private Cell[][] bombs;

    private Range ranges;
    private Map<Coord, Cell> bombsMap;

    @Getter
    private int totalBombsCount;

    public Bomb(Range ranges){
        this.ranges=ranges;
    }

    public void start(int bombCount) {

        totalBombsCount = 0;
//        bombs = new Cell[Game.X_SIZE][Game.Y_SIZE];
        bombsMap = new HashMap<>();
        generateBomb(bombCount);
        setAllNumbers();
    }


    private void generateBomb(int bombCount) {
        totalBombsCount = bombCount;
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int count = 0;
        while (count < bombCount) {
            int x = rnd.nextInt(Game.X_SIZE);
            int y = rnd.nextInt(Game.Y_SIZE);

            if (bombsMap.entrySet().stream().noneMatch(coordCellEntry -> coordCellEntry.getKey().getX() == x && coordCellEntry.getKey().getY() == y)) {
                bombsMap.put(new Coord(x, y), Cell.BOMB);
                count++;
            }

//            if(bombsList.stream().noneMatch(coord -> coord.getX()==x && coord.getY()==y)){
//                bombsList.add(new Coord(x,y));
//                count++;
//            }

//            if (bombs[x][y] != Cell.BOMB) {
//                bombs[x][y] = Cell.BOMB;
//                count++;
//            }
        }
    }

    private void setAllNumbers() {

        ranges.getCoordArrayList().forEach(coord -> {
            if(bombsMap.get(coord)!=Cell.BOMB){
                int i=calculateBombAround(coord);
                bombsMap.put(coord,Cell.values()[i] );
//                    numbersMap.put(new Coord(x,y), Cell.values()[i]);
            }
        });



//        Map<Coord, Cell> numbersMap = new HashMap<>();
//
//        for (int x = 0; x < Game.X_SIZE; x++) {
//            for (int y = 0; y < Game.Y_SIZE; y++) {
//                if(!bombsMap.containsKey(Coord.getTmpCoord(x,y))){
//                    int i=calculateBombAround(x, y);
//                    numbersMap.put(new Coord(x,y), Cell.values()[i]);
//                }
//            }
//        }
//
//        bombsMap.putAll(numbersMap);




//        for (int x = 0; x < Game.X_SIZE; x++) {
//            for (int y = 0; y < Game.Y_SIZE; y++) {
//
//
//                if (bombs[x][y] != Cell.BOMB) {
//                    int i = calculateBombAround(x, y);
//                    setNumber(x, y, i);
//                }
//            }
//        }
    }

//    public boolean isBomb(int x, int y) {
//        return bombs[x][y] == Cell.BOMB;
//    }

    public void setBombed(Coord coord) {
        bombsMap.put(coord, Cell.BOMBED);

//        bombs[x][y] = Cell.BOMBED;
    }

//    private int calculateBombAround(int x, int y) {
//        int count = 0;
//        for (int dx = -1; dx <= 1; dx++)
//            for (int dy = -1; dy <= 1; dy++) {
//                if (!(dx == 0 & dy == 0)) {
//                    if (x + dx >= 0 & x + dx < Game.X_SIZE & y + dy >= 0 & y + dy < Game.Y_SIZE){
//                        if(bombsMap.containsKey(Coord.getTmpCoord(x+dx, y+dy))){
//                            count++;
//                        }
//                    }
////                        if (bombs[x + sx][y + sy] == Cell.BOMB)
////                            count++;
//                }
//            }
//        return count;
//    }

    private int calculateBombAround(Coord coord) {
        int x = coord.getX();
        int y = coord.getY();
        AtomicInteger count = new AtomicInteger();
//        ranges.getCoordArrayList().forEach(c->{
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (!(dx == 0 & dy == 0))
                        if (x + dx >= 0 & x + dx < Game.X_SIZE & y + dy >= 0 & y + dy < Game.Y_SIZE){
                            {
                                if (bombsMap.get(Coord.getTmpCoord(x+dx, y+dy))!=Cell.BOMB) {
                                    count.getAndIncrement();
                                }
                            }
                        }
                }
            }
//        });

        return count.get();
    }

//    public void setNumber(int x, int y, int number) {
//        bombs[x][y] = Cell.values()[number];
//    }

    public Cell getBomb(Coord coord) {

        return bombsMap.get(coord);

//        return bombs[x][y];
    }
//
//    int getTotalBombsCount(){
//        return totalBombsCount;
//    }

}
