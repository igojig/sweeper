package ru.igojig.sweeper;


import lombok.Getter;

import java.util.List;

public class Game {
    private Bomb bomb;

    private Flag flag;

    private int boxesOpened;

    @Getter
    private Status status;

    @Getter
    private String infoStr;

    @Getter
    private static int colsX;
    @Getter
    private static int rowsY;

    private final String statusStr = "Bombs detected: [%d]. Total boxes opened: [%d]";

    public Game(int x, int y) {
        setSize(x, y);
    }

    public void setSize(int x, int y) {
        colsX = x;
        rowsY = y;
        Coordinate.initCoords();
    }

    public void start(int bomb_count) {

        bomb = new Bomb();
        flag = new Flag();
        bomb.start(bomb_count);
        flag.start();
        infoStr = Status.PLAYED.getName();
        status = Status.PLAYED;
        boxesOpened = 0;
    }

    public void leftMouse(int x, int y) {

        Coordinate tmpCoordinate = Coordinate.getCoordByXY(x, y).orElseThrow();

        if (status == Status.PLAYED) {
            switch (flag.getFlags(tmpCoordinate)) {
                case OPENED -> {
                }
                case CLOSED -> {
                    flag.setFlags(tmpCoordinate, Cell.OPENED);
                    boxesOpened++;
                    switch (bomb.getBomb(tmpCoordinate)) {

                        case BOMB:
                            bomb.setBombed(tmpCoordinate);
                            openAllField();
                            status = Status.BOMBED;
                            infoStr = status.getName() + String.format(statusStr, flag.getFlagCount(), boxesOpened);
                            break;
                        case ZERO:
                            boxesOpened += openBoxAround(tmpCoordinate);
                        default:
                            infoStr = String.format(statusStr, flag.getFlagCount(), boxesOpened);
                    }
                }
            }
        }
    }

    public void rightMouse(int x, int y) {

        Coordinate tmpCoordinate = Coordinate.getCoordByXY(x, y).orElseThrow();

        switch (status) {
            case PLAYED:
                flag.switchFlag(tmpCoordinate);
                infoStr = String.format(statusStr, flag.getFlagCount(), boxesOpened);
                if (flag.getFlagCount() == bomb.getTotalBombsCount()) {
                    if (checkFlagsEqualsToBombs()) {
                        openAllField();
                        status = Status.WIN;
                        infoStr = status.getName() + String.format(statusStr, flag.getFlagCount(), boxesOpened);
                    }
                }
                break;
        }
    }

    public Cell getBox(Coordinate coordinate) {
        if (flag.getFlags(coordinate) == Cell.OPENED) {
            return bomb.getBomb(coordinate);
        }
        return flag.getFlags(coordinate);
    }

    private int openBoxAround(Coordinate coordinate) {
        int count = 0;
        List<Coordinate> emptyArea = coordinate.getNearCoords();
        for (Coordinate c : emptyArea) {
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

    private boolean checkFlagsEqualsToBombs() {
        return Coordinate.getGameField().stream()
                .noneMatch(c->flag.getFlags(c) == Cell.FLAGGED && bomb.getBomb(c) != Cell.BOMB);
    }

    void openAllField() {
        for (Coordinate coordinate : Coordinate.getGameField()) {
            switch (flag.getFlags(coordinate)) {
                case FLAGGED -> {
                    if (bomb.getBomb(coordinate) != Cell.BOMB)
                        flag.setFlags(coordinate, Cell.NOBOMB);
                }
                case CLOSED, INFORM -> {
                    flag.setFlags(coordinate, Cell.OPENED);
                    boxesOpened++;
                }
            }
        }
    }

    public List<Coordinate> getGameField(){
        return Coordinate.getGameField();
    }
}
