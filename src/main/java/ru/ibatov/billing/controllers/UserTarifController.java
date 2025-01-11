package ru.ibatov.billing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ibatov.billing.dto.NewTarif;
import ru.ibatov.billing.dto.UserTarifWithResource;
import ru.ibatov.billing.entity.UserTarif;
import ru.ibatov.billing.service.UserTarifService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userTarif")
public class UserTarifController {

    private final UserTarifService userTarifService;

    @GetMapping("/")
    public List<UserTarif> getAllUserTarifs() {
        return userTarifService.getAll();
    }

    @GetMapping("/{id_phone}")
    public UserTarifWithResource getUserTarifs(@PathVariable int id_phone) {
        return userTarifService.getUserTarifs(id_phone);
    }

    @PostMapping("/")
    public List<UserTarif> saveUserTarif(@RequestBody NewTarif newTarif) {
        return userTarifService.save(newTarif);
    }

    @PostMapping("/wasteTarif")
    public void wasteTarifAllUsers(){
        userTarifService.wasteTarifs();
    }

    @PutMapping("/")
    public UserTarifWithResource updateUserTarif(@RequestBody NewTarif newTarif){
        return userTarifService.update(newTarif);
    }
}
