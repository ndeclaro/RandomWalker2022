package com.example.search;

import java.util.ArrayList;
import java.util.Random;

public class GroundTruthQueue {

    private ArrayList<String> truths;


    GroundTruthQueue(int x, int y){
        this.truths = new ArrayList<String>();
        this.truths.add(x+","+y);
    }
    public void remove(int index){
        this.truths.remove(index);
    }
    public void generate(ActionQueue actions, Grid grid){

        String pos = this.truths.get(truths.size()-1);
        String[] coords = pos.split(",");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        int rows = grid.getRows();
        int cols = grid.getCols();


        for(Action current : actions.getQueue()){

            //stay at an action
            Random stay = new Random();

            int check = stay.nextInt(10);
            if(check == 9){

                this.truths.add(pos);
                continue;
            }


            switch(current){

                case Down :
                    if(y == rows || grid.visit(x, y+1).getType() == CellType.BLOCKED ) {
                        this.truths.add(pos);
                    }else {
                        y++;
                        pos = x+","+y;
                        this.truths.add(pos);
                    }
                    break;
                case Right:
                    if(x == cols || grid.visit(x+1, y).getType() == CellType.BLOCKED){
                        this.truths.add(pos);
                    }else{
                        x++;
                        pos = x+","+y;
                        this.truths.add(pos);
                    }
                    break;
                case Up :
                    if(y == 1 || grid.visit(x, y-1).getType() == CellType.BLOCKED){
                        this.truths.add(pos);
                    }else{
                        y--;
                        pos = x+","+y;
                        this.truths.add(pos);
                    }
                    break;
                case Left:
                    if(x == 1 || grid.visit(x-1, y).getType() == CellType.BLOCKED){
                        this.truths.add(pos);
                    }else{
                        x--;
                        pos = x+","+y;
                        this.truths.add(pos);
                    }


            }



        }

    }
    /*
    public void noFail(Action action, Grid grid ){
        String pos = this.truths.get(truths.size()-1);
        String[] coords = pos.split(",");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        int rows = grid.getRows();
        int cols = grid.getCols();

        switch(action){

            case Down :
                if(y == rows || grid.visit(x, y+1).getType() == CellType.BLOCKED ) {
                    this.truths.add(pos);
                }else {
                    y++;
                    pos = x+","+y;
                    this.truths.add(pos);
                }
                break;
            case Right:
                if(x == cols || grid.visit(x+1, y).getType() == CellType.BLOCKED){
                    this.truths.add(pos);
                }else{
                    x++;
                    pos = x+","+y;
                    this.truths.add(pos);
                }
                break;
            case Up :
                if(y == 1 || grid.visit(x, y-1).getType() == CellType.BLOCKED){
                    this.truths.add(pos);
                }else{
                    y--;
                    pos = x+","+y;
                    this.truths.add(pos);
                }
                break;
            case Left:
                if(x == 1 || grid.visit(x-1, y).getType() == CellType.BLOCKED){
                    this.truths.add(pos);
                }else{
                    x--;
                    pos = x+","+y;
                    this.truths.add(pos);
                }

        }
    } */

    public ArrayList<String> getTruths(){
        return this.truths;
    }
    //return true if valid

    public void add(String coord){
        this.truths.add(coord);
    }

    public String print(){

        String returnable = "";
        for( String pos : this.truths){
            returnable = returnable + pos + " ";
        }
        return returnable;
    }

    public String get(int index){
        return this.truths.get(index);
    }
    public int  size() {
        return this.truths.size();
    }

    public void add(int i, String start) {
        truths.add(i, start);
    }
}
