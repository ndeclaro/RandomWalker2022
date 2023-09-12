package com.example.search;

import java.util.LinkedList;

public class Grid {
    private Node[][] ground;
    private int rows, cols;

    Grid(int rows, int cols){
        this.ground = new Node[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public Node[][] getGround(){
        return ground;
    }

    public void assignType(  int x, int y, CellType cell, BlockedMap blocked){
        Node temp = new Node(x, y, cell );

        ground[y-1][x-1] = temp;

        if(temp.getType() == CellType.BLOCKED){
            blocked.put(temp.getPos());
        }


    }





        public int getRows(){
            return this.rows;
        }
    public int getCols(){
        return this.cols;
    }

    public Node visit(int x, int y){
        return this.ground[y-1][x-1];
    }

    public Node visit(String pos){
        String[] split = pos.split(",");

        int x= Integer.parseInt(split[0]);
        int y = Integer.parseInt((split[1]));

        return this.ground[y-1][x-1];


    }

}
