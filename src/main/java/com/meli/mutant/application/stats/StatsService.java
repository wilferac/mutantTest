package com.meli.mutant.application.stats;

import com.meli.mutant.application.stats.domain.VerificationStats;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class StatsService {

    public Mono<VerificationStats> getVerificationStats() {
        return Mono.just(VerificationStats.builder().build());
    }

}
