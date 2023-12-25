package ru.igojig.sweeper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Objects;

//@AllArgsConstructor
@NoArgsConstructor
@Data
public class Coord {
    private int x;
    private int y;

    private static Coord tmpCoord=new Coord();

    public Coord(int x, int y){
        this.x=x;
        this.y=y;
        System.out.println(toString() + " [" + hashCode() + "]");
    }

    public Coord(Coord coord){
        this.x=coord.getX();
        this.y=coord.getY();
    }


    public static Coord getTmpCoord(int x, int y) {
        tmpCoord.setX(x);
        tmpCoord.setY(y);
        return tmpCoord;
    }

    @Override
    public int hashCode() {
        int tmp = ( y +  ((x+1)/2));
        return x +  (tmp * tmp);
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coord coord)) return false;
        return x == coord.x && y == coord.y;
    }
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
