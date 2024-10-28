package ru.ibatov.billing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ibatov.billing.dto.UserPhoneDto;
import ru.ibatov.billing.entity.Phone;
import ru.ibatov.billing.service.PhoneService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/phone")
public class PhoneController {

    private final PhoneService phoneService;

    @GetMapping("/")
    public List<Phone> getPhones(){
        return phoneService.findAllNumbers();
    }

    @PostMapping("/")
    public Phone savePhone(@RequestBody UserPhoneDto userPhoneDto){
        return phoneService.createPhone(userPhoneDto);
    }

    @DeleteMapping("/{id}")
    public void deletePhone(@PathVariable Long id){

    }

    @PutMapping("/")
    public void changePhoneInfo(){

    }
}
