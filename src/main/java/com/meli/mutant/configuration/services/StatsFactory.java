package com.meli.mutant.configuration.services;

import com.meli.mutant.application.human.HumanService;
import com.meli.mutant.application.stats.StatsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatsFactory {

    @Bean
    public StatsService getStatsService(HumanService humanService) {
        return new StatsService(humanService);
    }

}
