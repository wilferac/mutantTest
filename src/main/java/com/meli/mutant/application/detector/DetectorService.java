package com.meli.mutant.application.detector;

import com.meli.mutant.application.Human.HumanService;
import com.meli.mutant.application.detector.domain.DnaChain;
import com.meli.mutant.application.Human.domain.Human;
import com.meli.mutant.application.detector.validator.AdnValidatorInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class DetectorService {
    private final AdnValidatorInterface mutantValidator;
    private final HumanService humanService;

    public Mono<ResponseEntity> isMutant(DnaChain dnaChain) {
        if(!dnaChain.validateDna()){
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La cadena de ADN es invalida"));
        }

        Mono<Human> validatedHuman = mutantValidator.validateGeneticCode(Human.create(dnaChain))
                .flatMap(currentHuman -> humanService.save(currentHuman));

        return humanService.getByDna(dnaChain.getDna())
                .switchIfEmpty(validatedHuman)
                .map(currentHuman -> currentHuman.isMutant() ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

}
