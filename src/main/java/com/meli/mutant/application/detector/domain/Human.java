package com.meli.mutant.application.detector.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Human {
    List<String> dna;
    boolean isMutant;

}
