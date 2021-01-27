package com.seedcup.backend.global.dto;

import com.seedcup.backend.rank.dto.RankInfoDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SheetDto<T extends SheetData> {

    private List<Field> sheetInfo;

    private List<T> sheetData;

    public SheetDto(List<Field> fields) {
        this.sheetInfo = fields;
        this.sheetData = new ArrayList<>();
    }

    public void add(T e) {
        this.sheetData.add(e);
    }

    public void addAll(List<T> list) {
        this.sheetData.addAll(list);
    }
}
