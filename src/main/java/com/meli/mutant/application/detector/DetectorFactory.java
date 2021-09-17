package com.meli.mutant.application.detector;

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
    public DetectorService getDetectorService(MutantValidator mutantValidator) {
        return new DetectorService(mutantValidator);
    }


}
