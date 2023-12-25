package ru.igojig.sweeper;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {

//    private Matrix matrix;

    private Bomb bomb;
    private Range ranges;
    private Flag flag;

    private int boxesOpened;

    private Status status;
    private String infoStr;

    static int X_SIZE;
    static int Y_SIZE;

    public Game(int x, int y) {

        setSize(x, y);


//        ranges = new Range();
//        bomb = new Bomb();
//        flag = new Flag();
    }


    public void setSize(int x, int y) {
        X_SIZE = x;
        Y_SIZE = y;

    }

    public void start(int bomb_count) {
        ranges = new Range();
        bomb = new Bomb(ranges);
        flag = new Flag(ranges);
        bomb.start(bomb_count);
        flag.start();
        infoStr = Status.PLAYED.getName();
        status = Status.PLAYED;
        boxesOpened = 0;
    }


    public Range getRanges() {
        return ranges;
    }


    public void leftMouse(int x, int y) {

        Coord tmpCoord=new Coord(x,y);
//        Coord tmpCoord=new Coord(x,y);

        if (status == Status.PLAYED) {
            switch (flag.getFlags(tmpCoord)) {
                case OPENED -> {
                }
                case CLOSED -> {
                    flag.setFlags(tmpCoord, Cell.OPENED);
                    boxesOpened++;
                    switch (bomb.getBomb(tmpCoord)) {

                        case BOMB:
                            bomb.setBombed(tmpCoord);
                            openAllField();
//                            infoStr = "YOR ARE BOMBED ((( " + String.format("Bombs detected: %d  Total boxes opened: %d", flag.getFlagCount(), boxesOpened);
                            infoStr = String.format("YOR ARE BOMBED!!! Bombs detected: [%d]. Total boxes opened: [%d]", flag.getFlagCount(), boxesOpened);
                            status = Status.BOMBED;
                            break;
                        case ZERO:
                            boxesOpened += openBoxAround(tmpCoord);
                        default:
                            infoStr = String.format("Bombs detected: [%d]. Total boxes opened: [%d]", flag.getFlagCount(), boxesOpened);

                    }
                }
            }
        }
    }

    public void rightMouse(int x, int y) {
        Coord tmpCoord=new Coord(x,y);

        switch (status) {
            case PLAYED:
                flag.switchFlag(tmpCoord);
                infoStr = String.format("Bombs detected: [%d]. Total boxes opened: [%d]", flag.getFlagCount(), boxesOpened);
                //  statusString = "Bombs detected: " + flag.getFlagCount();
                if (flag.getFlagCount() == bomb.getTotalBombsCount()) {
                    if (checkFlagsToBombs()) {
                        openAllField();
                        infoStr =  String.format("YOU ARE WIN!!! Bombs detected: [%d]. Total boxes opened: [%d]", flag.getFlagCount(), boxesOpened);

                        status = Status.WIN;
                    }
                }
                break;
        }
    }

    public Cell getBox(Coord coord) {
        //   return matrix.getBox(coord.getX(), coord.getY());

        if (flag.getFlags(coord) == Cell.OPENED) {
            return bomb.getBomb(coord);
        }
        return flag.getFlags(coord);

    }

    public String getInfoStr() {
        return infoStr;
    }

    public Status getStatus(){
        return status;
    }

    private int openBoxAround(Coord coord) {
        List<Coord> area = new ArrayList<>();
        int count = 0;
        addArea(coord.getX(), coord.getY(), area);
        for (Coord c : area) {
            if (flag.getFlags(c) == Cell.CLOSED) {
                switch (bomb.getBomb(c)) {
                    case ZERO:
                        flag.setFlags(c, Cell.OPENED);
                        count++;
                        count += openBoxAround(c);
                        break;
                    default:
                        flag.setFlags(c, Cell.OPENED);
                        count++;
                }
            }
        }
        return count;
    }

    private void addArea(int x, int y, List<Coord> area) {
        for (int dx = -1; dx <= 1; dx++)
            for (int dy = -1; dy <= 1; dy++) {
                if (x + dx >= 0 & x + dx < X_SIZE & y + dy >= 0 & y + dy < Y_SIZE)
                    if (!(dx == 0 & dy == 0))
                        area.add(new Coord(x + dx, y + dy));
            }
    }

    private boolean checkFlagsToBombs() {
        boolean flag = true;
        for (Coord coord : ranges.getCoordArrayList()) {
            if (this.flag.getFlags(coord) == Cell.FLAGGED & bomb.getBomb(coord) != Cell.BOMB)
                flag = false;
        }
        return flag;
    }

    void openAllField() {
        for (Coord coord : ranges.getCoordArrayList()) {
            switch (flag.getFlags(coord)) {
                case FLAGGED:
                    if (bomb.getBomb(coord) != Cell.BOMB)
                        flag.setFlags(coord, Cell.NOBOMB);
                    break;
                case CLOSED:
                case INFORM:
                    flag.setFlags(coord, Cell.OPENED);
                    boxesOpened++;
                    break;

            }
        }
    }

}
