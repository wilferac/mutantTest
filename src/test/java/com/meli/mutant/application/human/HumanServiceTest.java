package com.meli.mutant.application.human;

import com.meli.mutant.application.human.adapter.HumanRepository;
import com.meli.mutant.application.human.domain.Human;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class HumanServiceTest {
    @Mock
    HumanRepository humanRepository;

    HumanService humanService;

    @BeforeEach
    void setup() {
        humanService = new HumanService(humanRepository);
    }

    @Test
    void shouldFilterByMutation() {
        var mockedData = List.of(Human.builder().build(), Human.builder().build());
        Mockito.when(humanRepository.filterByMutation(false)).thenReturn(Flux.fromIterable(mockedData));

        var result = humanService.filterByMutation(false);
        StepVerifier.create(result.collectList())
                .expectNext(mockedData)
                .verifyComplete();
    }

    @Test
    void shouldGetByDna() {
        var dnaChain = List.of("TAGC", "TAGC");
        var mockedData = Human.builder().id(1).dna(dnaChain).isMutant(false).build();
        Mockito.when(humanRepository.getByDna(dnaChain)).thenReturn(Mono.just(mockedData));

        var result = humanService.getByDna(dnaChain);
        StepVerifier.create(result)
                .expectNext(mockedData)
                .verifyComplete();
    }

    @Test
    void shouldSave() {
        var dnaChain = List.of("TAGC", "TAGC");
        var mockedData = Human.builder().id(1).dna(dnaChain).isMutant(false).build();
        var humanToSave = Human.builder().dna(dnaChain).isMutant(false).build();
        Mockito.when(humanRepository.save(humanToSave)).thenReturn(Mono.just(mockedData));

        var result = humanService.save(humanToSave);
        StepVerifier.create(result)
                .expectNext(mockedData)
                .verifyComplete();
    }

}