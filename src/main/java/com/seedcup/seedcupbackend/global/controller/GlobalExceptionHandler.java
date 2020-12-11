package com.seedcup.seedcupbackend.global.controller;

import com.seedcup.seedcupbackend.common.exception.PermissionDeniedException;
import com.seedcup.seedcupbackend.common.exception.UnAuthException;
import com.seedcup.seedcupbackend.global.dto.ResponseDto;
import com.seedcup.seedcupbackend.global.dto.StandardResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseDto<String> invalidValueExceptionHandler(MethodArgumentNotValidException e) {
        return StandardResponse.valueInvalid(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseDto<String> invalidParamExceptionHandler(ConstraintViolationException e) {
        return StandardResponse.valueInvalid(e.getMessage().split(",")[0]);
    }

    @ExceptionHandler(UnAuthException.class)
    @ResponseBody
    public ResponseDto<String> unAuthExceptionHandler() {
        return StandardResponse.notLogin();
    }

    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseBody
    public ResponseDto<String> permissionDeniedHandler() {
        return StandardResponse.permissionDenied();
    }
}
