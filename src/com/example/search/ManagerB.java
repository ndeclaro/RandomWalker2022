package com.example.search;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ManagerB {

    public static void main(String[] args) throws IOException {


        BufferedWriter out = null;
        try {
            FileWriter fstream = new FileWriter("ResultB.txt");
            out = new BufferedWriter(fstream);
            out.write("");



        int rows = 100;
        int cols = 50;


        int x =  randomX(cols);
        int y = randomY(rows);


        Grid base = new Grid(rows, cols);
        BlockedMap blocked = new BlockedMap();


        for(int i = 0 ; i<base.getRows(); i++){
            for(int j = 0; j<base.getCols(); j++ ){

                base.assignType(j+1, i+1, randomCell(), blocked );

            }
        }

        out.write(rows + " " + cols+"\n");


        for(int i = 0; i<base.getRows(); i++){
            for(int j = 0; j<base.getCols(); j++){
                int tx = j+1;
                int ty = i+1;
                out.write(ty + " " + tx + " " +  CellType.print( base.visit(tx, ty).getType() ) +"\n");
            }
        }

        out.write("\nPaths\n" );
        out.write(x + "," + y + "\n");

        ArrayList<GroundTruthQueue> pathTruths = new ArrayList<GroundTruthQueue>(); //101 measures
        ArrayList<ActionQueue> paths = new ArrayList<ActionQueue>(); //100 actions
        ArrayList<SensorQueue> sensedQ = new ArrayList<SensorQueue>(); //101 measures
        for(int i = 0; i<10; i++){
            ActionQueue current = new ActionQueue();
            actionList(current);
            paths.add(current );
            GroundTruthQueue currentGTQ = new GroundTruthQueue(x,y);
            currentGTQ.generate(current, base);
            pathTruths.add( currentGTQ);
            SensorQueue currentSen = new SensorQueue(base.visit(x,y).getType());
            currentSen.generate(currentGTQ, base);
            sensedQ.add(currentSen);

            //System.out.println(sensedQ.size());
        }
        for(int i = 0; i<10; i++){
            ActionQueue currentA = paths.get(i);
            GroundTruthQueue currentGTQ = pathTruths.get(i);
            SensorQueue currentSen = sensedQ.get(i);

            currentGTQ.remove(0);
            currentSen.remove(0);

            out.write("Path\n");

            out.write( currentA.printAsString() +"\n");
            out.write( currentGTQ.print() +"\n");
            out.write( currentSen.print() +"\n");

            out.write("\n");
        }


        }
        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        out.close();




    }

    public static CellType randomCell(){
        Random rand = new Random();
        int current = rand.nextInt(100);

       if (current < 50) {
           return CellType.NORMAL;
       }
       if(current<70) {
           return CellType.HIGHWAY;
       }
       if(current<90 ){
           return CellType.TRAVERSE;
       }

       return CellType.BLOCKED;
    }


    public static void actionList(ActionQueue actions){
        Random rand = new Random();

        while(actions.size() != 100){

            int current = rand.nextInt(4);


            switch (current){

                case  0 :
                    actions.add(Action.Down);
                    break;
                case 1 :
                    actions.add(Action.Up);
                    break;
                case 2 :
                    actions.add(Action.Left);
                    break;
                case 3 :
                    actions.add(Action.Right);
                    break;
                default:


            }




        }


    }
    public static int randomX(int x){

        Random rand = new Random();
        int ubound = x;
        return rand.nextInt(ubound) + 1;
    }

    public static int randomY(int y){
        Random rand = new Random();
        int ubound = y;
        return rand.nextInt(ubound) + 1;
    }
}
