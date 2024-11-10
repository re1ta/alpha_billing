package ru.ibatov.billing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ibatov.billing.dto.SortedTarifs;
import ru.ibatov.billing.entity.Tarif;
import ru.ibatov.billing.service.TarifService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tarif")
public class TarifController {

    private final TarifService tarifService;

    @GetMapping("/")
    public SortedTarifs getAllService(){
        return tarifService.findAllTarifs();
    }

    @PostMapping("/")
    public Tarif saveTarif(@RequestBody Tarif tarif){
        return tarifService.save(tarif);
    }

    @PutMapping("/")
    public List<Tarif> updateTarif(@RequestBody Tarif tarif){
        return tarifService.update(tarif);
    }

    @DeleteMapping("/{id}")
    public List<Tarif> deleteTarif(@PathVariable Long id){
        return tarifService.delete(id);
    }

}
