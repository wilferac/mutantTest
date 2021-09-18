package com.meli.mutant.application.stats.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;

@Value
@Builder
public class VerificationStats {
    //{“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4}
    BigInteger countMutantDna;
    BigInteger countHumanDna;
    Integer ratio;

}
