package com.seedcup.backend.rank.dto;

import com.seedcup.backend.global.dto.Field;
import com.seedcup.backend.global.dto.SheetData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RankInfoDto implements SheetData {

    private Integer teamId;

    private String teamName;

    private Double finalScore;

    public static List<Field> getFields() {
        List<Field> fields = new ArrayList<>(3);
        fields.add(new Field("id of team", "teamId", "Integer"));
        fields.add(new Field("name of team", "teamName", "String"));
        fields.add(new Field("final score", "finalScore", "Double"));
        return fields;
    }
}
