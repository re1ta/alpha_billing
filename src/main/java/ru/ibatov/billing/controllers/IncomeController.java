package ru.ibatov.billing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ibatov.billing.entity.History.Income;
import ru.ibatov.billing.service.IncomeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/income")
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping("/")
    public List<Income> allIncome() {
        return incomeService.getIncomes();
    }

    @GetMapping("/{id_phone}")
    public List<Income> userIncome(@PathVariable int id_phone) {
        return incomeService.getUserIncomes(id_phone);
    }

    @PostMapping("/")
    public Income deposit(@RequestBody Income income) {
        return incomeService.saveDeposit(income);
    }

}
