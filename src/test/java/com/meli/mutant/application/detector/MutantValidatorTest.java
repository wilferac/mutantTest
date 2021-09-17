package com.meli.mutant.application.detector;

import com.meli.mutant.application.detector.domain.Human;
import com.meli.mutant.application.detector.validator.MutantValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MutantValidatorTest {

    @InjectMocks
    MutantValidator mutantValidator;

    @Test
    public void shouldBeMutant() {
        List<String> dna = List.of("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");
        Human testSubject = Human.builder().dna(dna).build();

        Human expectedMutant = Human.builder().dna(dna).isMutant(true).build();
        Mono<Human> human = mutantValidator.validateGeneticCode(testSubject);

        StepVerifier.create(human).expectNext(expectedMutant).verifyComplete();
    }

    @Test
    public void shouldBeHuman() {
        List<String> dna = List.of("ATGCTA", "CAGTGC", "TTATGT", "AGAAGG", "CCTCTA", "TCACTG");
        Human testSubject = Human.builder().dna(dna).build();

        Human expectedMutant = Human.builder().dna(dna).isMutant(false).build();
        Mono<Human> human = mutantValidator.validateGeneticCode(testSubject);

        StepVerifier.create(human).expectNext(expectedMutant).verifyComplete();
    }

}