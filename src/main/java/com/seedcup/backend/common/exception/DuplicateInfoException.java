package com.seedcup.backend.common.exception;

import java.util.ArrayList;

public class DuplicateInfoException extends RuntimeException{

    private ArrayList<String> duplicateInfos = new ArrayList<>();
    public DuplicateInfoException() {
        super();
    }

    public void addDuplicateInfoField(String infoField) {
        this.duplicateInfos.add(infoField);
    }

    public ArrayList<String> getDuplicateInfos() {
        return duplicateInfos;
    }
}
