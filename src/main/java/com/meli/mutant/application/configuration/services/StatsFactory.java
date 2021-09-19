package com.meli.mutant.application.configuration.services;

import com.meli.mutant.application.Human.HumanService;
import com.meli.mutant.application.Human.adapter.HumanAdapter;
import com.meli.mutant.application.Human.adapter.HumanCrudRepository;
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
