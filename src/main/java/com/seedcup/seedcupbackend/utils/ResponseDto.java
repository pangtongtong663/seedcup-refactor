package com.seedcup.seedcupbackend.utils;

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
