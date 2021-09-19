package com.meli.mutant.application.human.adapter;

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

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class HumanAdapterTest {
    @Mock
    HumanCrudRepository humanCrudRepository;

    HumanAdapter humanAdapter;

    @BeforeEach
    void setup() {
        humanAdapter = new HumanAdapter(humanCrudRepository);
    }

    @Test
    void shouldFilterByMutation() {
        boolean isMutant = true;
        var humans = List.of(HumanEntity.builder().id(2).dna("[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]").isMutant(isMutant).build());
        Mockito.when(humanCrudRepository.findAllByIsMutant(isMutant)).thenReturn(Flux.fromIterable(humans));

        var expected = List.of(Human.builder().id(2).dna(List.of("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG")).isMutant(isMutant).build());
        var result = humanAdapter.filterByMutation(isMutant);

        StepVerifier.create(result.collectList())
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void shouldFilterByMutationInvalidData() {
        boolean isMutant = true;
        var humans = List.of(HumanEntity.builder().id(2).dna("[ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG]").isMutant(isMutant).build());
        Mockito.when(humanCrudRepository.findAllByIsMutant(isMutant)).thenReturn(Flux.fromIterable(humans));

        var expected = List.of(Human.builder().id(2).dna(Collections.emptyList()).isMutant(isMutant).build());
        var result = humanAdapter.filterByMutation(isMutant);

        StepVerifier.create(result.collectList())
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void shouldSave() {
        var humanEntityToSave = HumanEntity.builder().dna("[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]").isMutant(true).build();
        var savedHumanEntity = HumanEntity.builder().id(1).dna("[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]").isMutant(true).build();
        Mockito.when(humanCrudRepository.save(humanEntityToSave)).thenReturn(Mono.just(savedHumanEntity));

        var humanToSave = Human.builder().dna(List.of("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG")).isMutant(true).build();
        var expected = humanToSave.toBuilder().id(1).build();
        var result = humanAdapter.save(humanToSave);

        StepVerifier.create(result)
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void shouldGetByDna() {
        var plainDna = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]";
        var dna = List.of("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");
        var humanEntity = HumanEntity.builder().id(1).dna(plainDna).isMutant(true).build();
        Mockito.when(humanCrudRepository.findByDna(plainDna)).thenReturn(Mono.just(humanEntity));

        var expected = Human.builder().id(1).dna(dna).isMutant(true).build();
        var result = humanAdapter.getByDna(dna);

        StepVerifier.create(result)
                .expectNext(expected)
                .verifyComplete();
    }

}