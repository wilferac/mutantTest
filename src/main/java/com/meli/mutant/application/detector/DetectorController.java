package com.meli.mutant.application.detector;

import com.meli.mutant.application.detector.domain.DnaChain;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/detector")
public class DetectorController {
    private final DetectorService detectorService;

    @PostMapping("/mutant")
    @Operation(summary = "Validar si una cadena de adn pertenece a un mutante")
    public Mono<ResponseEntity> isMutant(@RequestBody DnaChain dnaChain) {
        return detectorService.isMutant(dnaChain);
    }

}
