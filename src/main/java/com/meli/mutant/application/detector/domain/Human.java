package com.meli.mutant.application.detector.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Human {
    Integer hash;
    List<String> dna;
    @Builder.Default
    boolean isMutant = false;

}
