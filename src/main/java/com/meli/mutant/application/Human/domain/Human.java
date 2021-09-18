package com.meli.mutant.application.Human.domain;

import com.meli.mutant.application.detector.domain.DnaChain;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Human {
    Integer id;
    List<String> dna;
    boolean isMutant;

    public static Human create(DnaChain dnaChain) {
        return Human.builder().dna(dnaChain.getDna()).isMutant(false).build();
    }

}
