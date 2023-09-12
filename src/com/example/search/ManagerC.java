package com.example.search;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ManagerC {


    public static void main(String[] args) throws IOException {

        String openPath = "ResultB.txt";
        String writePath = "ResultC.txt";
        String writePath2 = "ResultC2.txt";
        int PATHNO = 0;
        FileReader fr = null;
        try {
            fr = new FileReader(openPath);
            Scanner read = new Scanner(fr);

            int rows = read.nextInt();
            int cols = read.nextInt();


            read.nextLine();

            Grid base = new Grid(rows, cols);
            BlockedMap blocked = new BlockedMap();


            for(int i = 0; i<rows; i++){
                for(int j = 0; j <cols; j++ ){
                    int y = read.nextInt();
                    int x = read.nextInt();
                    CellType type = CellType.parse(read.next());
                    base.assignType(x, y, type, blocked);
                    read.nextLine();

                }
            }
            read.nextLine();
            read.nextLine();
            String start = read.nextLine();
            read.nextLine();
            String[] pos =  start.split(",");
            int startx = Integer.parseInt(pos[0]);
            int starty = Integer.parseInt(pos[1]);
            String remaining = "";
            while(read.hasNext()){
                remaining += read.nextLine() + "\n";
            }
           // System.out.println(remaining);

            String[] PathBox = remaining.split("Path\n");

            /*System.out.println(PathBox[2]);
            System.out.println("WACK");
            System.out.println(PathBox[3]);
            System.out.println("WACK");
            System.out.println(PathBox[4]);
            */


            String[] singlePath = PathBox[PATHNO].split("\n");
               /* System.out.println(singlePath[0]);
                System.out.println(singlePath[1]);
                System.out.println(singlePath[2]);
                System.out.println(singlePath.length); */
            ActionQueue actions = new ActionQueue();
            SensorQueue readings = new SensorQueue();
            GroundTruthQueue truths = new GroundTruthQueue(startx, starty);


             String[] actionAr = singlePath[0].split(" ");
            for(int i = 0; i<actionAr.length; i++){
                actions.add(Action.parse(actionAr[i]));
             //   System.out.println(actionAr[i] + " " + i);
            }

            String[] truthAr = singlePath[1].split( " ");
            for(int i = 0; i<truthAr.length; i++){
                truths.add(truthAr[i]);
             //   System.out.println(truthAr[i] + " " + i);
            }

            String[] senseAr = singlePath[2].split(" ");
            for(int i = 0; i<senseAr.length;i++){
                readings.add(CellType.parse(senseAr[i]));
              //  System.out.println(senseAr[i] + " " + i);

            }
           // System.out.println(actions.size());
           // System.out.println(truths.size());
           // System.out.println(readings.size());

            readings.remove(0);

            BufferedWriter out = null;



            double prior = 1.0 / (rows*cols - blocked.size());

            SEStorage storage = new SEStorage(rows, cols, prior, blocked,base);

            try {
                FileWriter fstream = new FileWriter(writePath);
                out = new BufferedWriter(fstream);
                out.write("");


                storage.create(actions, readings);
                storage.print(writePath);



            }
            catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }finally{
                if(out != null){
                    out.close();
                }
            }
            ArrayList<String> maxList = new ArrayList<String>();
          //  truths.add(0, start);
          //  maxList.add(start);
            for(int i =0; i<storage.size(); i++ ){
                String current = storage.get(i).findMax();
                maxList.add(current);

            }
            ArrayList<Integer> avgdiff = new ArrayList<Integer>();
            for(int i = 0; i<maxList.size(); i++){

                String[] maxCo = maxList.get(i).split(",");
                String[] truthCo = truths.get(i).split(",");

                int sum = 0;
                int maxx = Math.abs( Integer.parseInt(maxCo[0]) ) ;
                int maxy = Math.abs( Integer.parseInt(maxCo[1]) ) ;
                int truthx = Math.abs( Integer.parseInt(truthCo[0]) );
                int truthy = Math.abs( Integer.parseInt(truthCo[1]) );

                sum += Math.abs(maxx - truthx);
                sum += Math.abs(maxy - truthy);

                avgdiff.add(sum);

                //System.out.println(sum);
            }
            ArrayList<Double> truthprob = new ArrayList<Double>();
           // truthprob.add(prior);
            for(int i = 0; i<truths.size(); i++){
                truthprob.add( storage.get(i).probability(truths.get(i)) );

            }

            try {
                FileWriter fstream = new FileWriter(writePath2);
                out = new BufferedWriter(fstream);
                out.write("");

                for(int current : avgdiff){
                    out.write(current + " ");

                }
                out.write("\n");

                for(double current : truthprob){
                    out.write(current + " ");
                }
                out.write("\n");


            }
            catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }finally{
                if(out != null){
                    out.close();
                }
            }








        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }



    }
}
