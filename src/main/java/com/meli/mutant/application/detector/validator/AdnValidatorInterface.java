package com.meli.mutant.application.detector.validator;

import com.meli.mutant.application.human.domain.Human;
import reactor.core.publisher.Mono;

public interface AdnValidatorInterface {
    Mono<Human> validateGeneticCode(Human human);

}
