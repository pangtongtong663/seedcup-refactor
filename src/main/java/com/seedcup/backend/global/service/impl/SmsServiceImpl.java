package com.seedcup.backend.global.service.impl;

import com.seedcup.backend.global.exception.RepeatApplySmsTooFastException;
import com.seedcup.backend.global.service.RedisService;
import com.seedcup.backend.global.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService{

    @Autowired
    RedisService redisService;

    private Integer generateRandomCode () {
        Random r = new Random(LocalDateTime.now().getSecond());
        return r.nextInt(900000) + 100000;
    }
    @Override
    public void sendSms(String content) {

    }

    @Override
    public void sendSmsCode(String phoneNumber) throws RepeatApplySmsTooFastException{
        Integer code = generateRandomCode();
        if (redisService.getValue("sms:" + phoneNumber) != null) throw new RepeatApplySmsTooFastException();
        redisService.cacheValue("sms:" + phoneNumber, code.toString(), 90);
        log.info("sms code of number: " + phoneNumber + " is " + code);
    }

    @Override
    public boolean checkSmsCode(String phoneNumber, String inputCode) {
        String code = redisService.getValue("sms:" + phoneNumber);
        if (code == null) return false;
        if (code.equals(inputCode)) {
            redisService.removeValue("sms:" + phoneNumber);
            return true;
        } else return false;
    }
}
