package ru.igojig.sweeper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Coord {
    private int x;
    private int y;

//    private Box box;

//    public Coord(int x, int y) {
//        this.x = x;
//        this.y = y;
////        box=Box.CLOSED;
//    }

//    public int getX() {
//        return x;
//    }
//
//    public int getY() {
//        return y;
//    }

//    public Box getBox(){
//        return box;
//    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Coord coord)) return false;
//        return x == coord.x && y == coord.y;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(x, y);
//    }
//
//    @Override
//    public String toString() {
//        return "Coord{" +
//                "x=" + x +
//                ", y=" + y +
//                '}';
//    }
}
