package com.meli.mutant.application.Human.adapter;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HumanCrudRepository extends ReactiveCrudRepository<HumanEntity, Integer> {
    Flux<HumanEntity> findAll();

    Flux<HumanEntity> findAllByIsMutant(boolean isMutant);

    Mono<HumanEntity> findByDna(String dna);

    Mono<HumanEntity> save(HumanEntity humanEntity);

}

