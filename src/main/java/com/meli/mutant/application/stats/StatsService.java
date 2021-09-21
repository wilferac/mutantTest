package com.meli.mutant.application.stats;

import com.meli.mutant.application.human.HumanService;
import com.meli.mutant.application.human.domain.Human;
import com.meli.mutant.application.stats.domain.VerificationStats;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
public class StatsService {
    private static final NumberFormat DECIMAL_FORMAT = new DecimalFormat("#0.0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    private final HumanService humanService;

    public Mono<VerificationStats> getVerificationStats() {
        Mono<List<Human>> allHumans = humanService.filterByMutation(false).collectList().defaultIfEmpty(Collections.emptyList());
        Mono<List<Human>> allMutants = humanService.filterByMutation(true).collectList().defaultIfEmpty(Collections.emptyList());

        return Mono.zip(allHumans, allMutants)
                .map(data -> {
                    long totalHumans = data.getT1().size();
                    long totalMutants = data.getT2().size();
                    long totalPeople = totalHumans + totalMutants;

                    Double ratio = 0d;
                    if (totalPeople > 0) {
                        ratio = totalHumans == 0 ? 1D : (double) totalMutants / (double) totalPeople;
                    }

                    return VerificationStats.builder()
                            .countHumanDna(totalHumans)
                            .countMutantDna(totalMutants)
                            .ratio(DECIMAL_FORMAT.format(ratio))
                            .build();
                });
    }

}

