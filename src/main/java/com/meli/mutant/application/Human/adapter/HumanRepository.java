package com.meli.mutant.application.Human.adapter;

import com.meli.mutant.application.Human.domain.Human;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface HumanRepository {
    Flux<Human> getAll();

    Mono<Human> save(Human human);

    Mono<Human> getByDna(List<String> dna);
}
