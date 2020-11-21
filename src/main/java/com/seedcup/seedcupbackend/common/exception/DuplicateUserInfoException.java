package com.seedcup.seedcupbackend.common.exception;

import java.util.ArrayList;

public class DuplicateUserInfoException extends RuntimeException{

    private ArrayList<String> duplicateInfos = new ArrayList<>();
    public DuplicateUserInfoException() {
        super();
    }

    public void addDuplicateInfoField(String infoField) {
        this.duplicateInfos.add(infoField);
    }

    public ArrayList<String> getDuplicateInfos() {
        return duplicateInfos;
    }
}
