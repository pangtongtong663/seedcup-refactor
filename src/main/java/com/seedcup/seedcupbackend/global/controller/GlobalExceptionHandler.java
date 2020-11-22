package com.seedcup.seedcupbackend.global.controller;

import com.seedcup.seedcupbackend.global.dto.ResponseDto;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseDto<Object> exceptionHandler(MethodArgumentNotValidException e) {
        return new ResponseDto<>("103", "value invalid", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
