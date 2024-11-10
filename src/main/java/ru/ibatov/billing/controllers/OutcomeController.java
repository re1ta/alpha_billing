package ru.ibatov.billing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ibatov.billing.entity.History.Outcome;
import ru.ibatov.billing.service.OutcomeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/outcome")
public class OutcomeController {

    private final OutcomeService outcomeService;

    @GetMapping("/")
    public List<Outcome> allOutcomes() {
        return outcomeService.getOutcomes();
    }

    @GetMapping("/{id_phone}")
    public List<Outcome> userOutcomes(@PathVariable int id_phone) {
        return outcomeService.getUserOutcomes(id_phone);
    }

    @PostMapping("/{id_phone}")
    public Outcome payment(@PathVariable int id_phone) {
        return outcomeService.payForTarif(id_phone);
    }
}
