package com.example.search;

import java.util.ArrayList;
import java.util.Random;

public class SensorQueue {

    private ArrayList<CellType> readings;


    SensorQueue(){
        this.readings = new ArrayList<CellType>();
    }
    SensorQueue(CellType cell){
        this.readings = new ArrayList<CellType>();
        this.readings.add(cell);
    }

    public int size(){
        return this.readings.size();
    }

    public CellType pop(){
        CellType current = this.readings.get(0);
        this.readings.remove(0);
        return current;

    }
    public void remove(int index){
        this.readings.remove(index);
    }

    public void add(CellType cell){
        this.readings.add(cell);
    }

    public void generate(GroundTruthQueue GTQ, Grid grid){


        for(String pos : GTQ.getTruths() ){

            CellType current = grid.visit(pos).getType();

            Random wrong = new Random();

            int check = wrong.nextInt(100);
            if(check <5 ){

                if(current == CellType.NORMAL){
                    this.readings.add(CellType.HIGHWAY);
                }else if(current == CellType.HIGHWAY ){
                    this.readings.add(CellType.TRAVERSE);
                }else{
                    this.readings.add(CellType.NORMAL);
                }
                continue;
            }
            if(check < 10 ){
                if(current == CellType.NORMAL){
                    this.readings.add(CellType.TRAVERSE);
                }else if (current == CellType.HIGHWAY){
                    this.readings.add(CellType.NORMAL);
                }else{
                    this.readings.add(CellType.HIGHWAY);
                }
                continue;
            }

            this.readings.add(grid.visit(pos).getType());


        }

    }


    public String print(){

        String returnable = "";
        for(CellType current : this.readings){

            switch(current){

                case NORMAL -> returnable += "N ";
                case TRAVERSE -> returnable += "T ";
                case HIGHWAY -> returnable += "H ";


            }

        }
        return returnable;

    }
}
