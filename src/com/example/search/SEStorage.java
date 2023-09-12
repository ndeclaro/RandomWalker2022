package com.example.search;

import java.io.IOException;
import java.util.ArrayList;

public class SEStorage {

    private ArrayList<StateEstimate> SEStore;
    private int rows, cols, lastindex;
    private BlockedMap blocked;
    private Grid grid;


    SEStorage(int rows, int cols, double prior, BlockedMap blocked, Grid grid){
        this.SEStore = new ArrayList<StateEstimate>();
        this.rows = rows;
        this.cols = cols;
        this.blocked = blocked;
        this.grid = grid;
        this.lastindex = 0;
        this.SEStore.add(new StateEstimate(rows, cols, prior, blocked));

    }

    public StateEstimate get(int index){
        return this.SEStore.get(index);
    }

    public void create(ActionQueue actions, SensorQueue sensed ){
        if(actions.size() != sensed.size() ){
            return;
        }

        while(actions.size() != 0 ){

            Action current = actions.pop();
            CellType reading = sensed.pop();
                generateState(current, reading);

        }


    }

    private void generateState(Action action, CellType reading){
        // get prior stateEstimate
        StateEstimate prior = this.SEStore.get(SEStore.size()-1);
            double[][] priorAr = prior.getArr();
        //create current estimate
        StateEstimate current = new StateEstimate(prior.getRows(), prior.getCols());
            double[][] currentAr = current.getArr();
            double currprior = 0;
            double currentsum = 0;

            for(int i = 0; i<priorAr.length; i++){ //y
                for(int j = 0; j<priorAr[0].length; j++){ //x goes through all x first


                    currprior = actionCalc(priorAr, reading, action, i, j, rows, cols);
                    currentAr[i][j] = currprior;
                       //  System.out.println("AT " + i + " " + j + ": " + currprior);
                    currentsum += currprior;

                }
            }

        normalize(currentAr, currentsum);
          //System.out.println(currentsum);
        this.SEStore.add(current);

    }

    private void normalize(double[][] currentAr, double currentsum){

        for(int i = 0; i<currentAr.length; i++){
            for (int j = 0; j<currentAr[0].length ; j++){
                currentAr[i][j] = currentAr[i][j]/currentsum;
            }
        }

    }

    private double actionCalc(double[][] priorAr,
                              CellType reading, Action action,
                              int y, int x,
                              int rows, int cols){

        int xres = x+1;
        int yres = y+1;
        CellType standing = this.grid.visit(xres, yres).getType();
        if(this.grid.visit(x+1, y+1).getType() == CellType.BLOCKED){
                return 0;
        }


        double total = 0;

        switch(action){
            case Right : // move is the right
               if(x > 0  ){ // if there is a contributing cell to the left
                   //System.out.println("Right Made To " + x );
                   if(standing == reading){ // the reading is correct 90% of the time
                       total+= priorAr[y][x-1] * 0.9 * 0.9;

                   }else{
                       total+= priorAr[y][x-1] * 0.9 * 0.05;
                   }
               }
               if(xres < cols && priorAr[y][x+1] != 0){ // if the current cell can move to the right
                    //describing the situation of a failure of the movement
                   if(standing == reading){
                       total+= priorAr[y][x] * 0.1 * 0.9;
                   }else{
                       total+= priorAr[y][x] * 0.1 * 0.05;
                   }

               }else{
                   //if cell is the end cell then it stays 100% of the time with the correct reading 90% time

                   if(standing == reading ){
                       total+= priorAr[y][x] * 0.9;
                   }else{
                       total+= priorAr[y][x] * 0.05;
                   }
               }

               break;
            case Left :
                if(xres < cols  ){ // if there is a contributing cell to the right
                    //System.out.println("Left Made To " + x );
                    if(standing == reading){ // the reading is correct 90% of the time
                        total+= priorAr[y][x+1] * 0.9 * 0.9;

                    }else{
                        total+= priorAr[y][x+1] * 0.9 * 0.05;
                    }
                }
                if(x > 0 && priorAr[y][x-1] != 0){ // if the current cell can move to the left
                    //describing the situation of a failure of the movement
                    if(standing == reading){
                        total+= priorAr[y][x] * 0.1 * 0.9;
                    }else{
                        total+= priorAr[y][x] * 0.1 * 0.05;
                    }

                }else{
                    //if cell is the end cell then it stays 100% of the time with the correct reading 90% time

                    if(standing == reading ){
                        total+= priorAr[y][x] * 0.9;
                    }else{
                        total+= priorAr[y][x] * 0.05;
                    }
                }
                break;
            case Up :
                if(yres < rows ){ // if there is a contributing cell below
                    //System.out.println("Up Made To " + x );
                    if(standing == reading){ // the reading is correct 90% of the time
                        total+= priorAr[y+1][x] * 0.9 * 0.9;

                    }else{
                        total+= priorAr[y+1][x] * 0.9 * 0.05;
                    }
                }
                if(y > 0 && priorAr[y-1][x] != 0){ // if the current cell can move up
                    //describing the situation of a failure of the movement
                    if(standing == reading){
                        total+= priorAr[y][x] * 0.1 * 0.9;
                    }else{
                        total+= priorAr[y][x] * 0.1 * 0.05;
                    }

                }else{
                    //if cell is the end cell then it stays 100% of the time with the correct reading 90% time

                    if(standing == reading ){
                        total+= priorAr[y][x] * 0.9;
                    }else{
                        total+= priorAr[y][x] * 0.05;
                    }
                }



                break;
            case Down :
                if(y > 0  ){ // if there is a contributing cell above
                    //System.out.println("Down Made To " + x );
                    if(standing == reading){ // the reading is correct 90% of the time
                        total+= priorAr[y-1][x] * 0.9 * 0.9;

                    }else{
                        total+= priorAr[y-1][x] * 0.9 * 0.05;
                    }
                }
                if(yres < cols && priorAr[y+1][x] != 0){ // if the current cell can move down
                    //describing the situation of a failure of the movement
                    if(standing == reading){
                        total+= priorAr[y][x] * 0.1 * 0.9;
                    }else{
                        total+= priorAr[y][x] * 0.1 * 0.05;
                    }

                }else{
                    //if cell is the end cell then it stays 100% of the time with the correct reading 90% time

                    if(standing == reading ){
                        total+= priorAr[y][x] * 0.9;
                    }else{
                        total+= priorAr[y][x] * 0.05;
                    }
                }
                break;
        }


        return total;

    }




    public void add(StateEstimate state){
        this.SEStore.add(state);
        this.lastindex = lastindex++;
    }
    public void print(String path) throws IOException {
        for(StateEstimate current : SEStore){
            current.print(path);
        }
    }

    public int size(){
        return this.SEStore.size();
    }




}
