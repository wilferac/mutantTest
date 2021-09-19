package com.meli.mutant.application.detector.validator;

import com.meli.mutant.application.human.domain.Human;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

    @ParameterizedTest
    @MethodSource("dnaChains")
    void shouldValidateGeneticCode(List<String> dna, boolean isMutant, Integer validationsExecuted) {
        Human testSubject = Human.builder().dna(dna).build();

        Human expectedMutant = Human.builder().dna(dna).isMutant(isMutant).validChainsEvaluated(validationsExecuted).build();
        Mono<Human> human = mutantValidator.validateGeneticCode(testSubject);

        StepVerifier.create(human)
                .expectNext(expectedMutant)
                .verifyComplete();
    }

    static List<Arguments> dnaChains() {
        return List.of(
                Arguments.of(List.of(
                        "ATGCGA",
                        "CAGTGC",
                        "TTATGT",
                        "AGAAGG",
                        "CCCCTA",
                        "TCACTG"), true, 2),
                Arguments.of(List.of(
                        "AAAA",
                        "CAGT",
                        "TTAT",
                        "AGAA"), true, 2),
                Arguments.of(List.of(
                        "CTAA",
                        "CAAT",
                        "TAAT",
                        "AGAA"), true, 2),
                Arguments.of(List.of(
                        "CTAC",
                        "CAAT",
                        "TAAT",
                        "AGAA"), false, 1),
                Arguments.of(List.of(
                        "ATGCTA",
                        "CAGTGC",
                        "TTATGT",
                        "AGAAGG",
                        "CCTCTA",
                        "TCACTG"), false, 1),
                Arguments.of(List.of(
                        "AAAAAA",
                        "AAAAAA",
                        "AAAAAA",
                        "AAAAAA",
                        "AAAAAA",
                        "AAAAAA"), true, 2),
                Arguments.of(List.of(
                                "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT",
                                "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT",
                                "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT",
                                "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT"),
                        true, 5)
        );
    }

}