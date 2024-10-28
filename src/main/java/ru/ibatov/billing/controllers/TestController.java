package ru.ibatov.billing.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ibatov.billing.dto.PhoneDto;

@RestController
public class TestController {

    @PostMapping("/test")
    public String testRequest(@RequestBody PhoneDto phoneDto) {
        System.out.println(phoneDto.getPhoneNumber());
        return "Ваш телефон получен!";
    }
}
