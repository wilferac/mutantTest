package com.meli.mutant.application.detector.validator;

import com.meli.mutant.application.detector.domain.AdnNode;
import com.meli.mutant.application.detector.domain.EnumDnaSequence;
import com.meli.mutant.application.Human.domain.Human;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MutantValidator implements AdnValidatorInterface {
    private final static String HORIZONTAL_CHAIN = "H";
    private final static String VERTICAL_CHAIN = "V";
    private final static String DIAGONAL_CHAIN_UPPER_LEFT_RIGHT = "D1-";
    private final static String DIAGONAL_CHAIN_UPPER_RIGHT_LEFT = "D2-";
    private final static int MUTANT_DNA_LIMIT = 2;
    private final static int MUTANT_DNA_SEQUENCE_LIMIT = 4;

    @Override
    public Mono<Human> validateGeneticCode(final Human human) {
        Map<String, List<AdnNode>> flattedDna = mapDnaNodes(human.getDna(), human.getDna().get(0).length());

        return Flux.fromIterable(flattedDna.values())
                .map(this::validateDnaChain)
                .reduce(Integer::sum)
                .map(value -> value >= MUTANT_DNA_LIMIT ? human.toBuilder().isMutant(true).build() : human.toBuilder().isMutant(false).build());
    }

    private int validateDnaChain(List<AdnNode> adnNodes) {
        int chainsFound = 0;
        int totalCoincidences = 0;
        EnumDnaSequence lastDnaSequence = null;

        for (AdnNode adnNode : adnNodes) {
            if (adnNode.getDnaSequence().equals(lastDnaSequence)) {
                chainsFound++;
                if (chainsFound == MUTANT_DNA_SEQUENCE_LIMIT) {
                    chainsFound = 0;
                    lastDnaSequence = null;
                    totalCoincidences++;
                }
            } else {
                chainsFound = 1;
                lastDnaSequence = adnNode.getDnaSequence();
            }
        }

        return totalCoincidences;
    }

    /**
     * Flat the input DNA into lists
     */
    private Map<String, List<AdnNode>> mapDnaNodes(List<String> dna, int length) {
        Map<String, List<AdnNode>> allNodes = new HashMap<>();

        List<AdnNode> curNodes;
        for (int file = 0; file < length; file++) {
            curNodes = AdnNode.createFromString(dna.get(file));
            for (int column = 0; column < length; column++) {
                int d1 = file - column;
                int d2 = file + column;
                allNodes.put(HORIZONTAL_CHAIN + file, getNewNodes(allNodes, HORIZONTAL_CHAIN + file, curNodes.get(column)));
                allNodes.put(VERTICAL_CHAIN + column, getNewNodes(allNodes, VERTICAL_CHAIN + column, curNodes.get(column)));
                allNodes.put(DIAGONAL_CHAIN_UPPER_LEFT_RIGHT + d1, getNewNodes(allNodes, DIAGONAL_CHAIN_UPPER_LEFT_RIGHT + d1, curNodes.get(column)));
                allNodes.put(DIAGONAL_CHAIN_UPPER_RIGHT_LEFT + d2, getNewNodes(allNodes, DIAGONAL_CHAIN_UPPER_RIGHT_LEFT + d2, curNodes.get(column)));
            }
        }

        return allNodes;
    }

    private List<AdnNode> getNewNodes(Map<String, List<AdnNode>> allNodes, String key, AdnNode newNode) {
        List<AdnNode> curNodes = allNodes.getOrDefault(key, new ArrayList<>());
        curNodes.add(newNode);
        return curNodes;
    }

}
