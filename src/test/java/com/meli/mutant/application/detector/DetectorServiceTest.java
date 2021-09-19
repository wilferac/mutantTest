package com.meli.mutant.application.detector;

import com.meli.mutant.application.human.HumanService;
import com.meli.mutant.application.human.domain.Human;
import com.meli.mutant.application.detector.domain.DnaChain;
import com.meli.mutant.application.detector.validator.AdnValidatorInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class DetectorServiceTest {
    @Mock
    AdnValidatorInterface mutantValidator;
    @Mock
    HumanService humanService;

    DetectorService detectorService;

    @BeforeEach
    void setup() {
        detectorService = new DetectorService(mutantValidator, humanService);
    }

    @ParameterizedTest
    @MethodSource("humanData")
    void testIsMutant(List<String> dna, boolean isMutant, ResponseEntity expected) {
        var dnaChain = DnaChain.builder().dna(dna).build();
        var humanToTest = Human.create(dnaChain);
        var testedHuman = humanToTest.toBuilder().isMutant(isMutant).build();

        Mockito.when(humanService.getByDna(dnaChain.getDna())).thenReturn(Mono.empty());
        Mockito.when(mutantValidator.validateGeneticCode(humanToTest)).thenReturn(Mono.just(testedHuman));
        Mockito.when(humanService.save(testedHuman)).thenReturn(Mono.just(testedHuman.toBuilder().id(1).build()));

        var result = detectorService.isMutant(dnaChain);

        StepVerifier.create(result)
                .expectNext(expected)
                .verifyComplete();
    }

    @ParameterizedTest
    @MethodSource("invalidDna")
    void testIsMutantValidationError(List<String> dna) {
        var dnaChain = DnaChain.builder().dna(dna).build();

        var expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La cadena de ADN es invalida");
        var result = detectorService.isMutant(dnaChain);

        StepVerifier.create(result)
                .expectNext(expected)
                .verifyComplete();
    }

    static List<Arguments> humanData() {
        return List.of(
                Arguments.of(List.of("AAAA", "CCCC", "TTAT", "AGAA"), true, ResponseEntity.ok().build()),
                Arguments.of(List.of("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"), true, ResponseEntity.ok().build()),
                Arguments.of(List.of("ATGCTA", "CAGTGC", "TTATGT", "AGAAGG", "CCTCTA", "TCACTG"), false, ResponseEntity.status(HttpStatus.FORBIDDEN).build()),
                Arguments.of(List.of(
                                "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT",
                                "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT",
                                "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT",
                                "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT", "ATGCGAATGCGAATGCGAAT"),
                        true, ResponseEntity.ok().build())
        );
    }

    static List<Arguments> invalidDna() {
        return List.of(
                Arguments.of(List.of("ZTGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG")),
                Arguments.of(List.of("TGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG")),
                Arguments.of(List.of("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA")),
                Arguments.of(List.of("ATGCGA", "CAGTGC", "TTATGT", "AGAAXG", "CCCCTA", "TCACTG")),
                Arguments.of(List.of("ATG", "CAG", "TTA")),
                Arguments.of(List.of("ATGCGAATGCGAATGCGAATG")),
                Arguments.of(List.of("A"))
        );
    }

}