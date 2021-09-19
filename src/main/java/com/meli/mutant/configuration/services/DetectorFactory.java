package com.meli.mutant.configuration.services;

import com.meli.mutant.application.human.HumanService;
import com.meli.mutant.application.detector.DetectorService;
import com.meli.mutant.application.detector.validator.MutantValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DetectorFactory {

    @Bean
    public MutantValidator getMutantValidator() {
        return new MutantValidator();
    }

    @Bean
    public DetectorService getDetectorService(MutantValidator mutantValidator, HumanService humanService) {
        return new DetectorService(mutantValidator, humanService);
    }

}
