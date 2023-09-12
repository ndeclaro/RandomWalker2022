package com.example.search;

public class Node {
    private int x;
    private int y;
    private CellType type;

    Node(int x, int y, CellType type){
        this.x = x;
        this.y = y;
        this.type = type;

    }

    public String getPos(){
        return x+","+y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public CellType getType(){
        return this.type;
    }



}
