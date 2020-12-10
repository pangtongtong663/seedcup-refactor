package com.seedcup.seedcupbackend.global.service;


import com.seedcup.seedcupbackend.global.exception.RepeatApplySmsTooFastException;

public interface SmsService {

    void sendSms(String content);

    void sendSmsCode(String phoneNumber) throws RepeatApplySmsTooFastException;

    boolean checkSmsCode(String phoneNumber, String code);
}
