package ru.ibatov.billing.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ibatov.billing.entity.Names.Range;
import ru.ibatov.billing.repos.Names.RangeRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/range")
public class RangeController {

    private final RangeRepository rangeRepo;

    @GetMapping("/")
    public List<Range> allRanges(){
        return rangeRepo.findAll();
    }

    @PostMapping("/")
    public Range saveRange(@RequestBody Range range){
        return rangeRepo.save(range);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public List<Range> deleteRange(@PathVariable Long id){
        rangeRepo.deleteById(id);
        return rangeRepo.findAll();
    }

    @PutMapping("/")
    @Transactional
    public List<Range> updateRange(@RequestBody Range range){
        rangeRepo.updateById(range.getId(), range.getName());
        return rangeRepo.findAll();
    }
}
