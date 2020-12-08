package com.seedcup.seedcupbackend.common.exception;

import java.util.ArrayList;

public class DuplicateTeamInfoException extends RuntimeException{
    private ArrayList<String> duplicateInfos = new ArrayList<>();
    public DuplicateTeamInfoException(){
        super();
    }
    public void addDuplicateInfoField(String infoField){
        this.duplicateInfos.add(infoField);
    }
    public ArrayList<String> getDuplicateInfos(){
        return duplicateInfos;
    }
}
