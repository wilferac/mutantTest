package com.meli.mutant.application.detector;

import com.meli.mutant.application.detector.domain.Human;
import com.meli.mutant.application.detector.validator.AdnValidatorInterface;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class DetectorService {
    private final AdnValidatorInterface mutantValidator;

    public Mono<Human> isMutant(Human human){
        return mutantValidator.validateGeneticCode(human);
    }


}
