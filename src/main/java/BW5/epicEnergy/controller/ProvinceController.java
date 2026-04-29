package BW5.epicEnergy.controller;

import BW5.epicEnergy.entity.Provincia;
import BW5.epicEnergy.service.ProvinciaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/province")
@AllArgsConstructor
public class ProvinceController {
    private final ProvinciaService provinciaService;

    @GetMapping()
    public List<Provincia> findAllProvince(){
        return provinciaService.findAllProvince();
    }
}
