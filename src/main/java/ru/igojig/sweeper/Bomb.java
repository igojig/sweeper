package ru.igojig.sweeper;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor
public class Bomb {
    private Cell[][] bombs;

    @Getter
    private int totalBombsCount;

    public void start(int bombCount) {

        totalBombsCount = 0;
        bombs = new Cell[Game.X_SIZE][Game.Y_SIZE];
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
            if (bombs[x][y] != Cell.BOMB) {
                bombs[x][y] = Cell.BOMB;
                count++;
            }
        }
    }

    private void setAllNumbers() {
        for (int x = 0; x < Game.X_SIZE; x++) {
            for (int y = 0; y < Game.Y_SIZE; y++) {
                if (bombs[x][y] != Cell.BOMB) {
                    int i = calculateBombAround(x, y);
                    setNumber(x, y, i);
                }
            }
        }
    }

    public boolean isBomb(int x, int y) {
        return bombs[x][y] == Cell.BOMB;
    }

    public void setBombed(int x, int y) {
        bombs[x][y] = Cell.BOMBED;
    }

    private int calculateBombAround(int x, int y) {
        int count = 0;
        for (int sx = -1; sx <= 1; sx++)
            for (int sy = -1; sy <= 1; sy++) {
                if (!(sx == 0 & sy == 0)) {
                    if (x + sx >= 0 & x + sx < Game.X_SIZE & y + sy >= 0 & y + sy < Game.Y_SIZE)
                        if (bombs[x + sx][y + sy] == Cell.BOMB)
                            count++;
                }
            }
        return count;
    }

    public void setNumber(int x, int y, int number) {
        bombs[x][y] = Cell.values()[number];
    }

    public Cell getBomb(int x, int y) {
        return bombs[x][y];
    }
//
//    int getTotalBombsCount(){
//        return totalBombsCount;
//    }

}
