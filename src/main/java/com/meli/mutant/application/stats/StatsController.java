package com.meli.mutant.application.stats;

import com.meli.mutant.application.stats.domain.VerificationStats;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/stats")
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/verification")
    public Mono<VerificationStats> getVerificationStats() {
        return statsService.getVerificationStats();
    }

}
