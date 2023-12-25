package ru.igojig.sweeper;

import java.util.ArrayList;
import java.util.List;

public class Range {
    private final List<Coord> coordList;

    public Range(){
        coordList=new ArrayList<>();

        for(int i=0;i<Game.X_SIZE;i++)
            for(int j=0;j<Game.Y_SIZE;j++)
                coordList.add(new Coord(i,j));
    }

    public List<Coord> getCoordArrayList(){
        return coordList;
    }


}
