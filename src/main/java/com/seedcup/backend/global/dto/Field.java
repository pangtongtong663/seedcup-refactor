package com.seedcup.backend.global.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Field {

    private String name;

    private String index;

    private String type;
}
