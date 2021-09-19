package com.meli.mutant.application.stats;

import com.meli.mutant.application.Human.HumanService;
import com.meli.mutant.application.Human.domain.Human;
import com.meli.mutant.application.stats.domain.VerificationStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {
    @Mock
    HumanService humanService;

    StatsService statsService;

    @BeforeEach
    void setup() {
        statsService = new StatsService(humanService);
    }

    @ParameterizedTest
    @MethodSource("ratioData")
    void testGetVerificationStats(List<Human> humans, List<Human> mutants, VerificationStats expected) {
        Mockito.when(humanService.filterByMutation(false)).thenReturn(Flux.fromIterable(humans));
        Mockito.when(humanService.filterByMutation(true)).thenReturn(Flux.fromIterable(mutants));

        var result = statsService.getVerificationStats();

        StepVerifier.create(result).expectNext(expected).verifyComplete();
    }

    static List<Arguments> ratioData() {
        return List.of(
                Arguments.of(List.of(Human.builder().build(), Human.builder().build()),
                        List.of(Human.builder().build()),
                        VerificationStats.builder().countHumanDna(2L).countMutantDna(1L).ratio("0.3").build()),
                Arguments.of(List.of(Human.builder().build(), Human.builder().build()),
                        Collections.emptyList(),
                        VerificationStats.builder().countHumanDna(2L).countMutantDna(0L).ratio("0.0").build()),
                Arguments.of(List.of(Human.builder().build()),
                        List.of(Human.builder().build()),
                        VerificationStats.builder().countHumanDna(1L).countMutantDna(1L).ratio("0.5").build()),
                Arguments.of(Collections.emptyList(),
                        List.of(Human.builder().build(), Human.builder().build()),
                        VerificationStats.builder().countHumanDna(0L).countMutantDna(2L).ratio("1.0").build())
        );
    }

}