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
        bomb = new Bomb();
        flag = new Flag();
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

        if (status == Status.PLAYED) {
            switch (flag.getFlags(x, y)) {
                case OPENED -> {
                }
                case CLOSED -> {
                    flag.setFlags(x, y, Cell.OPENED);
                    boxesOpened++;
                    switch (bomb.getBomb(x, y)) {

                        case BOMB:
                            bomb.setBombed(x, y);
                            openAllField();
//                            infoStr = "YOR ARE BOMBED ((( " + String.format("Bombs detected: %d  Total boxes opened: %d", flag.getFlagCount(), boxesOpened);
                            infoStr = String.format("YOR ARE BOMBED!!! Bombs detected: [%d]. Total boxes opened: [%d]", flag.getFlagCount(), boxesOpened);
                            status = Status.BOMBED;
                            break;
                        case ZERO:
                            boxesOpened += openBoxAround(x, y);
                        default:
                            infoStr = String.format("Bombs detected: [%d]. Total boxes opened: [%d]", flag.getFlagCount(), boxesOpened);

                    }
                }
            }
        }
    }

    public void rightMouse(int x, int y) {

        switch (status) {
            case PLAYED:
                flag.switchFlag(x, y);
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

        if (Objects.requireNonNull(flag.getFlags(coord.getX(), coord.getY())) == Cell.OPENED) {
            return bomb.getBomb(coord.getX(), coord.getY());
        }
        return flag.getFlags(coord.getX(), coord.getY());

    }

    public String getInfoStr() {
        return infoStr;
    }

    public Status getStatus(){
        return status;
    }

    private int openBoxAround(int x, int y) {
        List<Coord> area = new ArrayList<>();
        int count = 0;
        addArea(x, y, area);
        for (Coord coord : area) {
            if (flag.getFlags(coord.getX(), coord.getY()) == Cell.CLOSED) {
                switch (bomb.getBomb(coord.getX(), coord.getY())) {
                    case ZERO:
                        flag.setFlags(coord.getX(), coord.getY(), Cell.OPENED);
                        count++;
                        count += openBoxAround(coord.getX(), coord.getY());
                        break;
                    default:
                        flag.setFlags(coord.getX(), coord.getY(), Cell.OPENED);
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
        boolean f = true;
        for (Coord coord : ranges.getCoordArrayList()) {
            if (flag.getFlags(coord.getX(), coord.getY()) == Cell.FLAGGED & bomb.getBomb(coord.getX(), coord.getY()) != Cell.BOMB)
                f = false;
        }
        return f;
    }

    void openAllField() {
        for (Coord coord : ranges.getCoordArrayList()) {
            switch (flag.getFlags(coord.getX(), coord.getY())) {
                case FLAGGED:
                    if (bomb.getBomb(coord.getX(), coord.getY()) != Cell.BOMB)
                        flag.setFlags(coord.getX(), coord.getY(), Cell.NOBOMB);
                    break;
                case CLOSED:
                case INFORM:
                    flag.setFlags(coord.getX(), coord.getY(), Cell.OPENED);
                    boxesOpened++;
                    break;

            }
        }
    }

}
