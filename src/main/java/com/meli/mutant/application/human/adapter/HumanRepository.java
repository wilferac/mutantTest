package com.meli.mutant.application.human.adapter;

import com.meli.mutant.application.human.domain.Human;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface HumanRepository {

    Flux<Human> filterByMutation(boolean isMutant);

    Mono<Human> save(Human human);

    Mono<Human> getByDna(List<String> dna);

}
