package com.example.search;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class StateEstimate {
    private static final int OFFSET = 1;
    private double[][] StateProb;

    StateEstimate(int rows, int cols, double prior, BlockedMap blocked){
        this.StateProb = new double[rows][cols];

        for(double[] row : StateProb){
            Arrays.fill(row, prior );
        }

        for(String coord : blocked.keySet()){
            String[] temp = coord.split(",");
            this.StateProb[Integer.parseInt(temp[1])-1][Integer.parseInt(temp[0])-1] = 0;
        }

        //System.out.print(prior);

    }
    StateEstimate(int rows, int cols){
        this.StateProb = new double[rows][cols];

    }


    public double[][] getArr(){
        return this.StateProb;
    }

    public int getRows(){
        return this.StateProb.length;
    }
    public int getCols(){
        return this.StateProb[0].length;
    }

    public String findMax(){
        double max = 0.0;
        String temp = "";
        for(int i = 0; i<StateProb.length; i++){
            for(int j = 0; j<StateProb[0].length; j++){
                if ( StateProb[i][j] > max) {
                    max = StateProb[i][j];
                    int x = j + 1;
                    int y = i + 1;

                    temp = x + "," + y;
                }
            }
        }
        return temp;
    }

    public double probability(String coord){
        String[] temp = coord.split(",");
        int x = Integer.parseInt(temp[0])-1;
        int y = Integer.parseInt(temp[1])-1;

        return StateProb[y][x];

    }

    public void print(String path) throws IOException {
        for(int i = 0; i<StateProb.length; i++){
          //  System.out.print((i+OFFSET) + ": ");
            for(int j = 0; j<StateProb[0].length; j++){
                System.out.print( StateProb[i][j] + ", " );
            }
            System.out.println();
        }
        System.out.println();

        //appending to result file
        BufferedWriter out = null;
        try {
            FileWriter fstream = new FileWriter(path, true);
            out = new BufferedWriter(fstream);
            for(int i = 0; i < StateProb.length; i++) {
                for (int j = 0; j < StateProb[0].length; j++) {
                    out.write(StateProb[i][j] + ", ");
                }
                out.write("\n");
            }
            out.write("\nGrid\n");

        }
        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        finally{
            if(out!= null){
                out.close();
            }
        }


    }





}
