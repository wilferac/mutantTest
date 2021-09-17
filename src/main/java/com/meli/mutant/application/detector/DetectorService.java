package com.meli.mutant.application.detector;

import com.meli.mutant.application.detector.domain.Human;
import com.meli.mutant.application.detector.validator.AdnValidatorInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class DetectorService {
    private final AdnValidatorInterface mutantValidator;

    public Mono<ResponseEntity> isMutant(Human human) {
        return mutantValidator.validateGeneticCode(human)
                .map(currentHuman -> currentHuman.isMutant() ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

}
