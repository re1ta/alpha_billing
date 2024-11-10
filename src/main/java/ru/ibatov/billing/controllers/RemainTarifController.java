package ru.ibatov.billing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ibatov.billing.entity.RemainTarif;
import ru.ibatov.billing.service.RemainTarifService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/remain")
public class RemainTarifController {

    private final RemainTarifService remainTarifService;

    @GetMapping("/")
    public List<RemainTarif> getAllTarif() {
        return remainTarifService.getAllRemainTarifs();
    }

    @PostMapping("/")
    public RemainTarif saveFullTarif(@RequestBody RemainTarif remainTarif){
        return remainTarifService.save(remainTarif);
    }

    @PutMapping("/")
    public void updateUserRemain(@RequestBody RemainTarif remainTarif){
        remainTarifService.update(remainTarif);
    }
}
