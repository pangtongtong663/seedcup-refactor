package com.seedcup.seedcupbackend.global.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseDto<T> {

    @NonNull
    private String code;

    @NonNull
    private String message;

    private T data;
}
