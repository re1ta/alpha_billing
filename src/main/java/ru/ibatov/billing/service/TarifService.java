package ru.ibatov.billing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.dto.SortedTarifs;
import ru.ibatov.billing.entity.Tarif;
import ru.ibatov.billing.repos.TarifRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TarifService {

    private final TarifRepository tarifRepo;
    private final UserTarifService userTarifService;

    public SortedTarifs findAllTarifs(){
        return userTarifService.getAllTarifs();
    }

    public Tarif save(Tarif tarif){
        return tarifRepo.save(tarif);
    }

    @Transactional
    public List<Tarif> update(Tarif tarif){
        tarifRepo.updateById(tarif.getId(), tarif.getId_range(), tarif.getId_typeResource(), tarif.getCoefficient());
        return tarifRepo.findAll();
    }

    @Transactional
    public List<Tarif> delete(Long id){
        tarifRepo.deleteById(id);
        return tarifRepo.findAll();
    }
}
