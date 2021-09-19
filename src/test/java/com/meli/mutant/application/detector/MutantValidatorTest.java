package com.meli.mutant.application.detector;

import com.meli.mutant.application.Human.domain.Human;
import com.meli.mutant.application.detector.validator.MutantValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class MutantValidatorTest {

    MutantValidator mutantValidator;

    @BeforeEach
    void setup() {
        mutantValidator = new MutantValidator();
    }

    @Test
    void shouldBeMutant() {
        List<String> dna = List.of("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");
        Human testSubject = Human.builder().dna(dna).build();

        Human expectedMutant = Human.builder().dna(dna).isMutant(true).build();
        Mono<Human> human = mutantValidator.validateGeneticCode(testSubject);

        StepVerifier.create(human).expectNext(expectedMutant).verifyComplete();
    }

    @Test
    void shouldBeHuman() {
        List<String> dna = List.of("ATGCTA", "CAGTGC", "TTATGT", "AGAAGG", "CCTCTA", "TCACTG");
        Human testSubject = Human.builder().dna(dna).build();

        Human expectedMutant = Human.builder().dna(dna).isMutant(false).build();
        Mono<Human> human = mutantValidator.validateGeneticCode(testSubject);

        StepVerifier.create(human).expectNext(expectedMutant).verifyComplete();
    }

}