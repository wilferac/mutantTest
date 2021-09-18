package com.meli.mutant.application.detector.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Value
@Builder
public class DnaChain {
    private static final Pattern PATTERN = Pattern.compile("[A,T,C,G]+");

    List<String> dna;

    @JsonIgnore
    public boolean validateDna() {
        if (!dna.isEmpty()) {
            int filesSize = dna.size();
            var sameSizeFiles = dna.stream()
                    .filter(chain -> chain.length() == filesSize && PATTERN.matcher(chain).matches())
                    .collect(Collectors.toList());

            return sameSizeFiles.size() == filesSize;
        }

        return false;
    }
}
