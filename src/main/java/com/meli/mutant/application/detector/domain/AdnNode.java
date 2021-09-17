package com.meli.mutant.application.detector.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class AdnNode {
    EnumDnaSequence dnaSequence;

    public static List<AdnNode> createFromString(String value) {
        return value.chars().mapToObj(e -> (char) e).map(charVal -> AdnNode.builder().dnaSequence(EnumDnaSequence.valueOf(String.valueOf(charVal))).build())
                .collect(Collectors.toList());
    }

}


