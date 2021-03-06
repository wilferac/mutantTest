package com.meli.mutant.configuration.services;

import com.meli.mutant.application.human.HumanService;
import com.meli.mutant.application.human.adapter.HumanAdapter;
import com.meli.mutant.application.human.adapter.HumanCrudRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HumanFactory {

    @Bean
    public HumanAdapter getHumanAdapter(HumanCrudRepository humanCrudRepository) {
        return new HumanAdapter(humanCrudRepository);
    }

    @Bean
    public HumanService getHumanService(HumanAdapter humanAdapter) {
        return new HumanService(humanAdapter);
    }


}
