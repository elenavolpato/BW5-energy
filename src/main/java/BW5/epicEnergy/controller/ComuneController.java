package BW5.epicEnergy.controller;

import BW5.epicEnergy.entity.Comune;
import BW5.epicEnergy.service.ComuneService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comune")
@AllArgsConstructor
public class ComuneController {
    private final ComuneService comuneService;

    @GetMapping("/provincia/{sigla}")
    public List<Comune> findAllComuniByProvince(@PathVariable String sigla){
        return comuneService.findAllComuniByProvincia(sigla);
    }

}
