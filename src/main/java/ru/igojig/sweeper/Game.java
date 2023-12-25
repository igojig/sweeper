package ru.igojig.sweeper;


import lombok.Getter;

import java.util.List;

public class Game {

    private Bomb bomb;

    @Getter
    private Range ranges;
    private Flag flag;

    private int boxesOpened;

    @Getter
    private Status status;

    @Getter
    private String infoStr;

    static int X_SIZE;
    static int Y_SIZE;

    private final String statusStr="Bombs detected: [%d]. Total boxes opened: [%d]";

    public Game(int x, int y) {
        setSize(x, y);
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

    public void leftMouse(int x, int y) {

        Coord tmpCoord = new Coord(x, y);

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
                            status = Status.BOMBED;
                            infoStr = status.getName() + String.format(statusStr, flag.getFlagCount(), boxesOpened);
                            break;
                        case ZERO:
                            boxesOpened += openBoxAround(tmpCoord);
                        default:
                            infoStr = String.format(statusStr, flag.getFlagCount(), boxesOpened);
                    }
                }
            }
        }
    }

    public void rightMouse(int x, int y) {
        Coord tmpCoord = new Coord(x, y);

        switch (status) {
            case PLAYED:
                flag.switchFlag(tmpCoord);
                infoStr = String.format(statusStr, flag.getFlagCount(), boxesOpened);
                if (flag.getFlagCount() == bomb.getTotalBombsCount()) {
                    if (checkFlagsToBombs()) {
                        openAllField();
                        status = Status.WIN;
                        infoStr =status.getName() +  String.format(statusStr, flag.getFlagCount(), boxesOpened);
                    }
                }
                break;
        }
    }

    public Cell getBox(Coord coord) {
        if (flag.getFlags(coord) == Cell.OPENED) {
            return bomb.getBomb(coord);
        }
        return flag.getFlags(coord);
    }

    private int openBoxAround(Coord coord) {
        int count = 0;
        List<Coord> emptyArea = coord.getNearCoords();
        for (Coord c : emptyArea) {
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
                case FLAGGED -> {
                    if (bomb.getBomb(coord) != Cell.BOMB)
                        flag.setFlags(coord, Cell.NOBOMB);
                }
                case CLOSED, INFORM -> {
                    flag.setFlags(coord, Cell.OPENED);
                    boxesOpened++;
                }
            }
        }
    }
}
