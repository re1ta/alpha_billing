package ru.ibatov.billing.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ibatov.billing.entity.Names.Range;
import ru.ibatov.billing.entity.Names.TypeResource;
import ru.ibatov.billing.repos.Names.RangeRepository;
import ru.ibatov.billing.repos.Names.TypeResourceRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resource")
public class ResourceController {

    private final TypeResourceRepository resourceRepo;

    @GetMapping("/")
    public List<TypeResource> allResources(){
        return resourceRepo.findAll();
    }

    @PostMapping("/")
    public TypeResource saveResource(@RequestBody TypeResource typeResource){
        return resourceRepo.save(typeResource);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public List<TypeResource> deleteResource(@PathVariable int id){
        resourceRepo.deleteById(id);
        return resourceRepo.findAll();
    }

    @PutMapping("/")
    @Transactional
    public List<TypeResource> updateResource(@RequestBody TypeResource typeResource){
        resourceRepo.updateById(typeResource.getId(), typeResource.getName());
        return resourceRepo.findAll();
    }
}
