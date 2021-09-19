package com.meli.mutant.application.detector;

import com.meli.mutant.application.detector.domain.DnaChain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class DetectorControllerTest {
    @Mock
    DetectorService detectorService;
    @InjectMocks
    DetectorController detectorController;

    @Test
    void testIsMutant() {
        var dnaChain = DnaChain.builder().dna(List.of("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG")).build();
        var mockedResult = ResponseEntity.ok().build();
        Mockito.when(detectorService.isMutant(dnaChain)).thenReturn(Mono.just(mockedResult));

        var result = detectorController.isMutant(dnaChain);
        StepVerifier.create(result)
                .expectNext(mockedResult)
                .verifyComplete();
    }


}