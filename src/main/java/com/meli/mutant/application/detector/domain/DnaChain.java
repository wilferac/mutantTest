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
    public final static int MUTANT_DNA_LIMIT = 2;
    public final static int MUTANT_DNA_SEQUENCE_LIMIT = 4;

    List<String> dna;

    @JsonIgnore
    public boolean validateDna() {
        int filesSize = dna.size();

        if (filesSize >= MUTANT_DNA_SEQUENCE_LIMIT) {

            var sameSizeFiles = dna.stream()
                    .filter(chain -> chain.length() == filesSize && PATTERN.matcher(chain).matches())
                    .collect(Collectors.toList());

            return sameSizeFiles.size() == filesSize;
        }

        return false;
    }
}
