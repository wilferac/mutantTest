package com.meli.mutant.application.stats.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VerificationStats {
    @JsonProperty("count_mutant_dna")
    Long countMutantDna;
    @JsonProperty("count_human_dna")
    Long countHumanDna;
    String ratio;

}
