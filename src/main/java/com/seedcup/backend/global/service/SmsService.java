package com.seedcup.backend.global.service;


import com.seedcup.backend.global.exception.RepeatApplySmsTooFastException;

public interface SmsService {

    void sendSms(String content);

    void sendSmsCode(String phoneNumber) throws RepeatApplySmsTooFastException;

    boolean checkSmsCode(String phoneNumber, String code);
}
