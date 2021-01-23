package com.seedcup.backend.global.controller;

import com.seedcup.backend.global.dto.ResponseDto;
import com.seedcup.backend.global.dto.StandardResponse;
import com.seedcup.backend.global.exception.RepeatApplySmsTooFastException;
import com.seedcup.backend.global.service.SmsService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Validated
@RestController
@RequestMapping(value = "/api/sms", produces = "application/json")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @RequestMapping(value = "/send_captcha", method = RequestMethod.GET)
    public ResponseDto<Object> sendSmsCode(@RequestParam @NotBlank(message = "Empty phone number")
                                               @Length(min = 11, max = 11, message = "Wrong format of phone number")
                                               @Pattern(regexp = "^1([38][0-9]|4[579]|5[^4]|6[6]|7[0135678]|9[89])\\d{8}$"
                                                       , message = "Wrong format of phone number") String phoneNumber) {
        try {
            smsService.sendSmsCode(phoneNumber);
            return StandardResponse.ok();
        } catch (RepeatApplySmsTooFastException e) {
            return StandardResponse.applyCaptchaTooFrequently();
        }
    }
}
