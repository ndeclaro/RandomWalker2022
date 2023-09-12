package com.example.search;

public enum CellType {
    NORMAL, BLOCKED, TRAVERSE, HIGHWAY;


    public static CellType parse(String string){

        if(string.contains("N") ){
            return NORMAL;
        }else if(string.contains("H")){
            return HIGHWAY;
        }else if(string.contains("T")){
            return TRAVERSE;
        }else if(string.contains("B")){
            return BLOCKED;
        }
        return null;
    }


    public static String print(CellType type) {

        switch(type){
            case BLOCKED ->{
                return "B";
            }

            case HIGHWAY ->{
                return "H";
            }
            case NORMAL -> {
                return "N";
            }
            case TRAVERSE -> {
                return "T";
            }
        }
        return null;
    }
}

