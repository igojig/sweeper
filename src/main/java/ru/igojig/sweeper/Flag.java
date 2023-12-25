package ru.igojig.sweeper;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Flag {

    private Cell[][] flags;

    @Getter
    private int flagCount;

    void start(){
        flags = new Cell[Game.X_SIZE][Game.Y_SIZE];
        initFlag();
        flagCount=0;
    }

    void initFlag(){
        for(int x=0;x<Game.X_SIZE;x++)
            for(int y=0;y<Game.Y_SIZE;y++)
                flags[x][y]= Cell.CLOSED;
    }

    public void switchFlag(int x, int y) {
        switch (flags[x][y]) {
            case CLOSED -> {
                flags[x][y] = Cell.FLAGGED;
                flagCount++;
            }
            case FLAGGED -> {
                flags[x][y] = Cell.INFORM;
                flagCount--;
            }
            case INFORM -> flags[x][y] = Cell.CLOSED;
        }
    }

    public Cell getFlags(int x, int y){
        return flags[x][y];
    }

    public void setFlags(int x, int y, Cell cell){
        flags[x][y]= cell;
    }

//    public int getFlagCount(){
//        return flagCount;
//    }
}
