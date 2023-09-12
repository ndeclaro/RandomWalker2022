package com.example.search;

import java.util.LinkedList;

public class ActionQueue {

    private LinkedList<Action> queue;

    ActionQueue(){
        this.queue = new LinkedList<Action>();
    }

    public void add(Action action){
        this.queue.add(action);
    }

    public Action pop(){
        return this.queue.removeFirst();
    }


    public int size() {
        return this.queue.size();
    }

    public LinkedList<Action> getQueue(){
        return this.queue;
    }
    public String printAsString(){

        String building = "";
        for(Action current : this.queue){

            switch(current){
                case Right -> building+="R ";
                case Left -> building +="L ";
                case Down -> building += "D ";
                case Up -> building += "U ";

            }
        }

        return building;
    }
}
