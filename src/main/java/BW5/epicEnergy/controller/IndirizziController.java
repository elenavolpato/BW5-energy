package BW5.epicEnergy.controller;

import BW5.epicEnergy.entity.Indirizzo;
import BW5.epicEnergy.service.IndirizzoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/indirizzi")
@AllArgsConstructor
public class IndirizziController {
    private final IndirizzoService indirizzoService;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIndirizzo(@PathVariable UUID id) {
        indirizzoService.deleteIndirizzo(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Indirizzo> update(@PathVariable UUID id, @RequestBody Indirizzo details){
        Indirizzo updated = indirizzoService.updateIndirizzo(id, details);
        return ResponseEntity.ok(updated);
    }
}
