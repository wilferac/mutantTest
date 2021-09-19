package com.meli.mutant.application.Human.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.mutant.application.Human.domain.Human;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class HumanAdapter implements HumanRepository {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final HumanCrudRepository humanCrudRepository;

    @Override
    public Flux<Human> filterByMutation(boolean isMutant) {
        return humanCrudRepository.findAllByIsMutant(isMutant).map(this::mapToHuman);
    }

    @Override
    public Mono<Human> save(Human human) {
        return humanCrudRepository.save(mapToHumanEntity(human)).map(entity -> human.toBuilder().id(entity.getId()).build());
    }

    @Override
    public Mono<Human> getByDna(List<String> dna) {
        return humanCrudRepository.findByDna(getPlainDna(dna)).map(this::mapToHuman);
    }

    private Human mapToHuman(HumanEntity humanEntity) {
        List<String> dna = Collections.emptyList();
        try {
            dna = OBJECT_MAPPER.readValue(humanEntity.getDna(), OBJECT_MAPPER.getTypeFactory().constructCollectionType(Collection.class, String.class));
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("unable to map to HumanEntity to Human", jsonProcessingException);
        }

        return Human.builder().id(humanEntity.getId()).dna(dna).isMutant(humanEntity.getIsMutant()).build();
    }

    private HumanEntity mapToHumanEntity(Human human) {
        return HumanEntity.builder()
                .dna(getPlainDna(human.getDna()))
                .isMutant(human.isMutant())
                .build();
    }

    private String getPlainDna(List<String> dna) {
        try {
            return OBJECT_MAPPER.writeValueAsString(dna);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("unable to map to get PlainDna", jsonProcessingException);
            return "";
        }
    }
}
