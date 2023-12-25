package ru.igojig.sweeper;

public class Matrix {
    private Cell[][] matrix;

    Matrix(){
        matrix=new Cell[Game.X_SIZE][Game.Y_SIZE];
        initMatrix();
    }

    private void initMatrix(){
        for(int x=0;x<Game.X_SIZE;x++)
            for(int y=0;y<Game.Y_SIZE;y++)
                matrix[x][y]= Cell.CLOSED;
    }

    Cell getBox(int x, int y){
        return matrix[x][y];
    }

    public void setBox(int x, int y, Cell cell){
        matrix[x][y]= cell;
    }

}
