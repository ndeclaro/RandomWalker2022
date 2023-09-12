package com.example.search;

public enum Action {
    Up, Down, Left, Right;

    public static Action parse(String string){

        if(string.contains("U")){
            return Action.Up;
        }
        if(string.contains("D")){
            return Action.Down;
        }
        if(string.contains("L")){
            return Action.Left;
        }
        if(string.contains("R")){
            return Action.Right;
        }
        return null;

    }
}
