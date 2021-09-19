package com.meli.mutant.application.Human;

import com.meli.mutant.application.Human.adapter.HumanRepository;
import com.meli.mutant.application.Human.domain.Human;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
public class HumanService {
    private final HumanRepository humanRepository;

    public Flux<Human> getAll() {
        return humanRepository.getAll();
    }

    public Flux<Human> filterByMutation(boolean isMutant) {
        return humanRepository.filterByMutation(isMutant);
    }

    public Mono<Human> getByDna(List<String> dna) {
        return humanRepository.getByDna(dna);
    }

    public Mono<Human> save(Human human) {
        return humanRepository.save(human);
    }

}
