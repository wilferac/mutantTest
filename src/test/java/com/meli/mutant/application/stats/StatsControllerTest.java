package com.meli.mutant.application.stats;

import com.meli.mutant.application.stats.domain.VerificationStats;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class StatsControllerTest {
    @Mock
    StatsService statsService;

    @InjectMocks
    StatsController statsController;

    @Test
    void shouldGetVerificationStats() {
        VerificationStats mocked = VerificationStats.builder().countHumanDna(10L).countHumanDna(1L).ratio("0.1").build();
        Mockito.when(statsService.getVerificationStats()).thenReturn(Mono.just(mocked));

        var result = statsController.getVerificationStats();

        StepVerifier.create(result)
                .expectNext(mocked)
                .verifyComplete();
    }


}